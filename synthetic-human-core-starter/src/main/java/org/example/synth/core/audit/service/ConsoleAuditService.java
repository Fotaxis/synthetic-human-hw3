package org.example.synth.core.audit.service;

import lombok.extern.slf4j.Slf4j;
import org.example.synth.core.audit.model.AuditEvent;
import org.example.synth.core.audit.service.AuditService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@ConditionalOnProperty(name = "synth.core.audit.mode", havingValue = "CONSOLE", matchIfMissing = true)
public class ConsoleAuditService implements AuditService {

    @Override
    public void audit(AuditEvent event) {
        log.info("AUDIT EVENT: {}", event);
    }
}
