package org.example.synth.core.audit.service;

import org.example.synth.core.audit.model.AuditEvent;

public interface AuditService {
    void audit(AuditEvent event);
}
