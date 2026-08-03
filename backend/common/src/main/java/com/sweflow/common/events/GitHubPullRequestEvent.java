package com.sweflow.common.events;

public record GitHubPullRequestEvent(
    String repository,
    Integer pullRequestNumber,
    String action,
    String author
) {
}
