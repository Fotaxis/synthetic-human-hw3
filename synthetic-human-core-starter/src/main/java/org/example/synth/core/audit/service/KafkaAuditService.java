package org.example.synth.core.audit.service;

import lombok.RequiredArgsConstructor;
import org.example.synth.core.audit.model.AuditEvent;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "synth.core.audit.mode", havingValue = "KAFKA")
@RequiredArgsConstructor
public class KafkaAuditService implements AuditService {
    private final KafkaTemplate<String, AuditEvent> kafkaTemplate;

    @Override
    public void audit(AuditEvent event) {
        kafkaTemplate.send("audit-events", event);
    }
}
