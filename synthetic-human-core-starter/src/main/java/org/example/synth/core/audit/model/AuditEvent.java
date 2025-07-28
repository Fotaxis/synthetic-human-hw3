package org.example.synth.core.audit.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class AuditEvent {
    private String actor;
    private String action;
    private String details;
    private Instant timestamp;
}
