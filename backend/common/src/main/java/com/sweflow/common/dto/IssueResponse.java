package com.sweflow.common.dto;

public record IssueResponse(
        String repository,
        String title,
        String description
) {
}
