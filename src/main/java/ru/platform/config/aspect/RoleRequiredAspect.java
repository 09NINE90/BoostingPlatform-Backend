package ru.platform.config.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.platform.config.custom_annotation.RoleRequired;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Aspect
@Component
public class RoleRequiredAspect {

    private static final Logger logger = LoggerFactory.getLogger(RoleRequiredAspect.class);

    @Around("@annotation(roleRequired)")
    public Object checkRole(ProceedingJoinPoint joinPoint, RoleRequired roleRequired) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getAuthorities().isEmpty()) {
            logger.warn("Access denied: User is not authenticated");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not authenticated");
        }

        List<String> requiredRoles = Arrays.asList(roleRequired.value());
        List<String> userRoles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        logger.info("Attempting access: User '{}' with roles {} is trying to call {}",
                authentication.getName(), userRoles, joinPoint.getSignature().toShortString());

        boolean hasRole = userRoles.stream().anyMatch(requiredRoles::contains);
        if (!hasRole) {
            logger.warn("Access denied: User '{}' does not have the required roles {}",
                    authentication.getName(), requiredRoles);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have the required role");
        }

        logger.info("Access granted: User '{}' is executing {}", authentication.getName(), joinPoint.getSignature().toShortString());

        return joinPoint.proceed();
    }
}

