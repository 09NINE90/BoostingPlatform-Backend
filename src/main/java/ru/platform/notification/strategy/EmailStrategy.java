package ru.platform.notification.strategy;

import ru.platform.user.dao.UserEntity;

import java.util.Map;
import java.util.Properties;

public interface EmailStrategy {
    void sendEmail(UserEntity user, Properties properties, Object dto);

    String getTemplateName();

    String getSubject();

    Map<String, Object> buildModel(UserEntity user, Object dto);
}