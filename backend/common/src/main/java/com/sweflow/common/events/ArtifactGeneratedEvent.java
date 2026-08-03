package com.sweflow.common.events;

import com.sweflow.common.enums.ArtifactType;

import java.util.UUID;

public record ArtifactGeneratedEvent(
        UUID eventId,
        UUID artifactId,
        UUID workflowId,
        UUID workflowStepId,
        UUID issueId,
        String repository,
        ArtifactType artifactType,
        String storagePath,
        String createdByAgent
) {

}
