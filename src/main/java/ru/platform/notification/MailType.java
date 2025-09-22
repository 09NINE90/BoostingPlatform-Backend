package ru.platform.notification;

import lombok.Getter;

@Getter
public enum MailType {
    REGISTRATION,
    PASSWORD_RECOVERY,
    START_SESSION,
    FINISH_SESSION
}