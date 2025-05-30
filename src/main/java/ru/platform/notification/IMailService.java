package ru.platform.notification;

import ru.platform.user.dao.UserEntity;

import java.util.Properties;

public interface IMailService {
    void sendMail(UserEntity user, MailType type, Properties properties);
    void sendMail(UserEntity user, MailType type);

}
