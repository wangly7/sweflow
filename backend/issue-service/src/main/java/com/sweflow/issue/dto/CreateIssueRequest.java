package com.sweflow.issue.dto;

public record CreateIssueRequest(
        String title,
        String description,
        String repository,
        String createdBy
) {
}
