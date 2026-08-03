package com.sweflow.issue.dto;

import com.sweflow.issue.entity.IssueStatus;

import java.util.UUID;

public record CreateIssueResponse(
        UUID issueId,
        IssueStatus status
) {
}
