package com.sweflow.common.events;

import java.util.UUID;

public record ReviewJobEvent(
        UUID eventId,
        UUID jobId,
        UUID issueId,
        String repository,
        Integer prNumber,
        String prUrl
) {
}
