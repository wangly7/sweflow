package com.sweflow.common.events;

import com.sweflow.common.enums.PullRequestEventType;

import java.util.UUID;

public record PullRequestEvent(
        UUID eventId,
        UUID workflowId,
        UUID workflowStepId,
        UUID issueId,
        String repository,
        Integer prNumber,
        String prUrl,
        String branchName,
        PullRequestEventType eventType,
        String source
) {
}
