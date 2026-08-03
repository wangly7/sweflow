package com.sweflow.common.constants;

public final class KafkaTopics {
    private KafkaTopics () {}

    public static final String ISSUE_EVENTS = "issue-events";
    public static final String DOC_JOBS = "doc-jobs";
    public static final String ARTIFACT_EVENTS = "artifact-events";
    public static final String CODING_JOBS = "coding-jobs";
    public static final String PR_EVENTS = "pr-events";
    public static final String REVIEW_JOBS = "review-jobs";
    public static final String REVIEW_RESULTS = "review-results";
}
