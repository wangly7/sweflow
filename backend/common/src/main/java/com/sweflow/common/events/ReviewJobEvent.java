package com.sweflow.common.events;

import java.util.UUID;

public record ReviewJobEvent(
        UUID eventId,
        UUID workflowId,
        UUID workflowStepId,
        UUID issueId,
        Integer prNumber
) {
}
