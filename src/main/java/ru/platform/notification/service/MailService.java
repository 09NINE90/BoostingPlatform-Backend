package ru.platform.notification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.platform.exception.ErrorType;
import ru.platform.exception.PlatformException;
import ru.platform.notification.MailType;
import ru.platform.notification.strategy.EmailStrategy;
import ru.platform.notification.strategy.EmailStrategyFactory;
import ru.platform.user.dao.UserEntity;

import java.util.Properties;

@Service
@RequiredArgsConstructor
public class MailService implements IMailService {

    private final EmailStrategyFactory emailStrategyFactory;

    @Override
    public void sendMail(UserEntity user, MailType type, Properties properties, Object dto) {
        try {
            EmailStrategy strategy = emailStrategyFactory.getStrategy(type);
            strategy.sendEmail(user, properties, dto);
        } catch (Exception e) {
            throw new PlatformException(ErrorType.EMAIL_SEND_ERROR);
        }
    }

    @Override
    public void sendMail(UserEntity user, MailType type) {
        sendMail(user, type, null, null);
    }

    @Override
    public void sendMail(UserEntity user, MailType type, Object dto) {
        sendMail(user, type, null, dto);
    }

}
