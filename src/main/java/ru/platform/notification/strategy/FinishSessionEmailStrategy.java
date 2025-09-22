package ru.platform.notification.strategy;

import freemarker.template.Configuration;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import ru.platform.orders.dto.FinishSessionMailDto;
import ru.platform.user.dao.UserEntity;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
@RequiredArgsConstructor
public class FinishSessionEmailStrategy implements EmailStrategy {

    private final Configuration configuration;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String MAIL_FROM;

    @Value("${FRONTEND_ORIGIN:http://localhost:5173}")
    private String frontendOrigins;

    @Override
    @SneakyThrows
    public void sendEmail(UserEntity user, Properties properties, Object dto) {
        FinishSessionMailDto finishSessionEmailStrategy = (FinishSessionMailDto) dto;

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
        helper.setSubject(getSubject());
        helper.setTo(user.getUsername());
        helper.setFrom(new InternetAddress(MAIL_FROM));

        String emailContent = getEmailContent(getTemplateName(), buildModel(user, finishSessionEmailStrategy));
        helper.setText(emailContent, true);

        javaMailSender.send(mimeMessage);
    }

    @Override
    public String getTemplateName() {
        return "finishSession.html";
    }

    @Override
    public String getSubject() {
        return "Finish session";
    }

    @Override
    public Map<String, Object> buildModel(UserEntity user, Object dto) {
        FinishSessionMailDto finishSessionMailDto = (FinishSessionMailDto) dto;
        Map<String, Object> model = new HashMap<>();
        model.put("username", user.getProfile().getNickname());
        model.put("gameName", finishSessionMailDto.getGameName());
        model.put("serviceName", finishSessionMailDto.getServiceName());
        model.put("sessionNotes", finishSessionMailDto.getSessionNotes());
        model.put("plannedDuration", finishSessionMailDto.getPlanedSessionDuration());
        model.put("actualDuration", finishSessionMailDto.getFactSessionDuration());
        model.put("endTime", finishSessionMailDto.getEndTime());
        model.put("reportLink", finishSessionMailDto.getImgurLink());
        String orderLink = String.format(
                "%s/chat/%s/%s",
                frontendOrigins,
                finishSessionMailDto.getChatRoomId(),
                finishSessionMailDto.getOrderId()
        );
        model.put("orderLink", orderLink);
        return model;
    }

    @SneakyThrows
    private String getEmailContent(String templateName, Map<String, Object> model) {
        StringWriter stringWriter = new StringWriter();
        configuration.getTemplate(templateName).process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }
}
