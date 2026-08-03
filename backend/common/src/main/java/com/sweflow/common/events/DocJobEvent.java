package com.sweflow.common.events;

import com.sweflow.common.enums.WorkflowStepType;

import java.util.UUID;

public record DocJobEvent(
        UUID eventId,
        UUID workflowId,
        UUID workflowStepId,
        UUID issueId,
        String title,
        String description,
        String repository,
        WorkflowStepType stepType
) {
}
