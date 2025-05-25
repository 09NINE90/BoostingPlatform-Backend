package ru.platform.notification;

import freemarker.template.Configuration;
import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.platform.exception.ErrorType;
import ru.platform.exception.PlatformException;
import ru.platform.monitoring.MonitoringMethodType;
import ru.platform.monitoring.PlatformMonitoring;
import ru.platform.user.dao.UserEntity;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class MailService implements IMailService {

    private final Configuration configuration;
    private final JavaMailSender javaMailSender;

    @Override
    public void sendMail(UserEntity user, MailType type, Properties properties) {
        switch (type) {
            case REGISTRATION -> sendRegistrationEmail(user, properties);
            case PASSWORD_RECOVERY -> sendPasswordRecoveryEmail(user, properties);
            default -> {}
        }
    }

    @Override
    public void sendMail(UserEntity user, MailType type) {
        try {
            sendMail(user, type, null);
        }catch (Exception e) {
            throw new PlatformException(ErrorType.EMAIL_SEND_ERROR);
        }
    }

    @SneakyThrows
    @PlatformMonitoring(name = MonitoringMethodType.SEND_REGISTRATION_MAIL)
    private void sendRegistrationEmail(UserEntity user, Properties properties){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
        helper.setSubject("Confirmation registration");
        helper.setTo(user.getUsername());
        String emailContent = getRegistrationEmailContent(user);
        helper.setText(emailContent, true);
        javaMailSender.send(mimeMessage);
    }

    @SneakyThrows
    private String getRegistrationEmailContent(UserEntity user){
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("confirmationToken", user.getConfirmationToken());
        configuration.getTemplate("registration.html")
                .process(model,stringWriter);
        return stringWriter.getBuffer().toString();
    }

    @SneakyThrows
    @PlatformMonitoring(name = MonitoringMethodType.SEND_RECOVERY_PASSWORD_MAIL)
    private void sendPasswordRecoveryEmail(UserEntity user, Properties properties){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
        helper.setSubject("Password recovery");
        helper.setTo(user.getUsername());
        String emailContent = getPasswordRecoveryEmailContent(user);
        helper.setText(emailContent, true);
        javaMailSender.send(mimeMessage);
    }

    @SneakyThrows
    private String getPasswordRecoveryEmailContent(UserEntity user){
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("confirmationToken", user.getConfirmationToken());
        configuration.getTemplate("recoveryPassword.html")
                .process(model,stringWriter);
        return stringWriter.getBuffer().toString();
    }
}
