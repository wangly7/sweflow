package com.sweflow.common.events;

import java.util.UUID;

public record CodingJobEvent(
        UUID eventId,
        UUID workflowId,
        UUID workflowStepId,
        UUID issueId,
        UUID artifactId,
        String repository,
        String storagePath,
        String targetBranch
) {
}
