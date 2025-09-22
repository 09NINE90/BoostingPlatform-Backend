package ru.platform.notification.strategy;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import ru.platform.orders.dto.StartSessionMailDto;
import ru.platform.user.dao.UserEntity;
import freemarker.template.Configuration;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
@RequiredArgsConstructor
public class StartSessionEmailStrategy implements EmailStrategy {

    private final Configuration configuration;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String MAIL_FROM;

    @Value("${FRONTEND_ORIGIN:http://localhost:5173}")
    private String frontendOrigins;

    @Override
    @SneakyThrows
    public void sendEmail(UserEntity user, Properties properties, Object dto) {
        StartSessionMailDto startSessionMailDto = (StartSessionMailDto) dto;

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
        helper.setSubject(getSubject());
        helper.setTo(user.getUsername());
        helper.setFrom(new InternetAddress(MAIL_FROM));

        String emailContent = getEmailContent(getTemplateName(), buildModel(user, startSessionMailDto));
        helper.setText(emailContent, true);

        javaMailSender.send(mimeMessage);
    }

    @Override
    public String getTemplateName() {
        return "startSession.html";
    }

    @Override
    public String getSubject() {
        return "Start session";
    }

    @Override
    public Map<String, Object> buildModel(UserEntity user, Object dto) {
        StartSessionMailDto startSessionMailDto = (StartSessionMailDto) dto;
        Map<String, Object> model = new HashMap<>();
        model.put("username", user.getProfile().getNickname());
        model.put("sessionDuration", startSessionMailDto.getSessionDuration());
        model.put("gameName", startSessionMailDto.getGameName());
        model.put("serviceName", startSessionMailDto.getServiceName());
        model.put("startTime", startSessionMailDto.getStartTime());
        String streamLink = startSessionMailDto.getStreamLink();
        String streamContent;
        if (streamLink != null && !streamLink.isEmpty()) {
            streamContent = "<a href=\"" + streamLink + "\" style=\"color: #6a9eff; text-decoration: underline\">Stream link</a>";
        } else {
            streamContent = "<p>No stream link available</p>";
        }
        model.put("streamContent", streamContent);
        String orderLink = String.format(
                "%s/chat/%s/%s",
                frontendOrigins,
                startSessionMailDto.getChatRoomId(),
                startSessionMailDto.getOrderId()
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
