package ru.platform.monitoring;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.platform.utils.JwtUtil;

@Aspect
@Component
@RequiredArgsConstructor
public class MonitoringAspect {

    private final MeterRegistry meterRegistry;
    private final JwtUtil jwtUtil;

    @Around("@annotation(monitoring)")
    public Object monitor(ProceedingJoinPoint pjp, PlatformMonitoring monitoring) throws Throwable {
        Timer.Sample sample = Timer.start(meterRegistry);

        String status = "success";
        String exceptionName = "none";

        String token = jwtUtil.extractTokenFromRequest();
        String username = jwtUtil.extractUsername(token);

        try {
            return pjp.proceed();
        } catch (Exception e) {
            status = "error";
            exceptionName = e.getClass().getSimpleName();
            throw e;
        } finally {
            sample.stop(Timer.builder(monitoring.name().getName())
                    .description("Time spent in method")
                    .tag("description", monitoring.name().getDescription())
                    .tag("username", username)
                    .tag("class", pjp.getTarget().getClass().getSimpleName())
                    .tag("method", pjp.getSignature().getName())
                    .tag("status", status)
                    .tag("exception", exceptionName)
                    .register(meterRegistry));

            meterRegistry.counter("method.calls",
                    "description", monitoring.name().getDescription(),
                    "username", username,
                    "method", monitoring.name().name(),
                    "class", pjp.getTarget().getClass().getSimpleName(),
                    "status", status
            ).increment();

            if (status.equals("error")) {
                meterRegistry.counter("method.errors",
                        "description", monitoring.name().getDescription(),
                        "username", username,
                        "method", monitoring.name().name(),
                        "exception", exceptionName
                ).increment();
            }
        }

    }
}