package com.sweflow.codingagent.service;

import com.sweflow.codingagent.producer.PullRequestEventProducer;
import com.sweflow.common.enums.PullRequestEventType;
import com.sweflow.common.events.CodingJobEvent;
import com.sweflow.common.events.PullRequestEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class CodingAgentService {
    private final PullRequestEventProducer pullRequestEventProducer;

    public void execute(CodingJobEvent event) {
        String branchName = "ai/isssue-" + event.eventId();

        PullRequestEvent pullRequestEvent = new PullRequestEvent(
                UUID.randomUUID(),
                event.workflowId(),
                event.workflowStepId(),
                event.issueId(),
                1,
                "https://github.com/example/example/pull/1",
                branchName,
                PullRequestEventType.OPENED,
                "coding-agent"
        );
        pullRequestEventProducer.publish(pullRequestEvent);
        log.info(
                "Completed mock coding job. workflowId={}, issueId={}, branchName={}",
                event.workflowId(),
                event.issueId(),
                branchName
        );
    }
}
