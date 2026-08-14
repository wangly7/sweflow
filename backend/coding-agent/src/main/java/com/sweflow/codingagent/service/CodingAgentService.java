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
        Path designDocPath = Path.of(event.storagePath());

        if (!Files.exists(designDocPath)) {
            throw new IllegalStateException(
                    "Design document does not exist: " + designDocPath
            );
        }

        String branchName = "ai/isssue-" + event.eventId();

        PullRequestEvent pullRequestEvent = new PullRequestEvent(
                UUID.randomUUID(),
                event.workflowId(),
                event.workflowStepId(),
                event.issueId(),
                event.repository(),
                1,
                "https://github.com/example/example/pull/1",
                branchName,
                PullRequestEventType.OPENED,
                "coding-agent"
        );
        pullRequestEventProducer.publish(pullRequestEvent);
        log.info(
                "Completed mock coding job. worfklowId={}, issueId={}, branchName={}",
                event.workflowId(),
                event.issueId(),
                branchName
        );
    }
}
