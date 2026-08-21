package com.sweflow.common.events;

import com.sweflow.common.enums.ReviewVerdict;

import java.util.UUID;

public record ReviewResultEvent(
        UUID eventId,
        UUID workflowId,
        UUID workflowStepId,
        UUID issueId,
        Integer prNumber,
        ReviewVerdict reviewVerdict,
        String summary,
        String feedback
) {
}
