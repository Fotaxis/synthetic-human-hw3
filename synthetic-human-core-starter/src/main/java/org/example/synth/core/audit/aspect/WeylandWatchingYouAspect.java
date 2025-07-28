package org.example.synth.core.audit.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.synth.core.audit.model.AuditEvent;
import org.example.synth.core.audit.service.AuditService;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
@RequiredArgsConstructor
public class WeylandWatchingYouAspect {
    private final AuditService auditService;

    @AfterReturning("@annotation(org.example.synth.core.audit.aspect.WeylandWatchingYou)")
    public void auditMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        String action = method.getDeclaringClass().getSimpleName() + "." + method.getName();

        String details = Arrays.stream(joinPoint.getArgs())
                .map(String::valueOf)
                .collect(Collectors.joining(", ", "Params: [", "]"));

        AuditEvent event = AuditEvent.builder()
                .actor("")
                .action(action)
                .details(details)
                .timestamp(Instant.now())
                .build();

        auditService.audit(event);
    }
}
