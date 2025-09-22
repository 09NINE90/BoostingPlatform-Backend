package ru.platform.notification.service;

import ru.platform.notification.MailType;
import ru.platform.user.dao.UserEntity;

import java.util.Properties;

public interface IMailService {
    void sendMail(UserEntity user, MailType type, Properties properties, Object dto);
    void sendMail(UserEntity user, MailType type, Object dto);
    void sendMail(UserEntity user, MailType type);

}
