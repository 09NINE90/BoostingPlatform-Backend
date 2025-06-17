package ru.platform.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.platform.annotation.RoleRequired;

import java.util.Arrays;
import java.util.List;

import ru.platform.exception.PlatformException;
import ru.platform.user.dao.UserEntity;
import ru.platform.user.service.IAuthService;

import static ru.platform.exception.ErrorType.ACCESS_DENIED_ERROR;

@Aspect
@Component
@RequiredArgsConstructor
public class RoleRequiredAspect {

    private final IAuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(RoleRequiredAspect.class);

    @Around("@annotation(roleRequired)")
    public Object checkRole(ProceedingJoinPoint joinPoint, RoleRequired roleRequired) throws Throwable {
        UserEntity user = authService.getAuthUser();

        if (user == null) {
            logger.error("Access denied: User is not authenticated");
            throw new PlatformException(ACCESS_DENIED_ERROR);
        }

        List<String> requiredRoles = Arrays.asList(roleRequired.value());
        String userRoles = user.getRoles();

        logger.debug("Attempting access: User '{}' with roles {} is trying to call {}",
                user.getUsername(), userRoles, joinPoint.getSignature().toShortString());

        boolean hasRole = requiredRoles.stream().anyMatch(role -> role.equals(userRoles));
        if (!hasRole) {
            logger.error("Access denied: User '{}' does not have the required roles {}",
                    user.getUsername(), requiredRoles);
            throw new PlatformException(ACCESS_DENIED_ERROR);
        }

        logger.debug("Access granted: User '{}' is executing {}", user.getUsername(), joinPoint.getSignature().toShortString());

        return joinPoint.proceed();
    }
}

