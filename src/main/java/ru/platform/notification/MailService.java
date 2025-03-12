package ru.platform.notification;

import freemarker.template.Configuration;
import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
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
            default -> {}
        }
    }

    @Async
    @Override
    public void sendMail(UserEntity user, MailType type) {
        sendMail(user, type, null);
    }

    @SneakyThrows
    private void sendRegistrationEmail(UserEntity user, Properties properties){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
        helper.setSubject("Confirmation Email");
        helper.setTo(user.getUsername());
        String emailContent = getRegistrationEmailContent(user);
        helper.setText(emailContent, true);
        javaMailSender.send(mimeMessage);
    }

    @SneakyThrows
    private String getRegistrationEmailContent(UserEntity user){
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("confirmationCode", user.getConfirmationCode());
        configuration.getTemplate("registration.html")
                .process(model,stringWriter);
        return stringWriter.getBuffer().toString();
    }
}
