package com.sweflow.common.events;

import java.util.UUID;

public record IssueCreatedEvent(
    UUID eventId,
    UUID issueId,
    String title,
    String description,
    String repository,
    String createdBy
) {
}
