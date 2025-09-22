package ru.platform.notification.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.platform.notification.MailType;

@Component
@RequiredArgsConstructor
public class EmailStrategyFactory {
    private final RegistrationEmailStrategy registrationStrategy;
    private final PasswordRecoveryEmailStrategy passwordRecoveryStrategy;
    private final StartSessionEmailStrategy startSessionStrategy;
    private final FinishSessionEmailStrategy finishSessionEmailStrategy;

    public EmailStrategy getStrategy(MailType type) {
        return switch (type) {
            case REGISTRATION -> registrationStrategy;
            case PASSWORD_RECOVERY -> passwordRecoveryStrategy;
            case START_SESSION -> startSessionStrategy;
            case FINISH_SESSION -> finishSessionEmailStrategy;
        };
    }
}