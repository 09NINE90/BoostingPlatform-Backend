package ru.platform.notification.strategy;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import ru.platform.exception.ErrorType;
import ru.platform.exception.PlatformException;
import ru.platform.user.dao.UserEntity;
import freemarker.template.Configuration;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
@RequiredArgsConstructor
public class RegistrationEmailStrategy implements EmailStrategy {

    private final Configuration configuration;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String MAIL_FROM;

    @Value("${FRONTEND_ORIGIN:http://localhost:5173}")
    private String frontendOrigins;

    @Override
    public void sendEmail(UserEntity user, Properties properties, Object dto) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            helper.setSubject(getSubject());
            helper.setTo(user.getUsername());
            helper.setFrom(new InternetAddress(MAIL_FROM));

            String emailContent = getEmailContent(getTemplateName(), buildModel(user, dto));
            helper.setText(emailContent, true);

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new PlatformException(ErrorType.EMAIL_SEND_ERROR);
        }
    }

    @Override
    public String getTemplateName() {
        return "registration.html";
    }

    @Override
    public String getSubject() {
        return "Confirmation registration";
    }

    @Override
    public Map<String, Object> buildModel(UserEntity user, Object dto) {
        Map<String, Object> model = new HashMap<>();
        model.put("confirmationToken", user.getConfirmationToken());
        model.put("frontendOrigins", frontendOrigins);
        return model;
    }

    @SneakyThrows
    private String getEmailContent(String templateName, Map<String, Object> model) {
        StringWriter stringWriter = new StringWriter();
        configuration.getTemplate(templateName).process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }
}
