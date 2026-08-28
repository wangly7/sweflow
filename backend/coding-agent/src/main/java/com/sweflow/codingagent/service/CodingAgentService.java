package com.sweflow.codingagent.service;

import com.sweflow.codingagent.client.ArtifactClient;
import com.sweflow.codingagent.context.CodingContext;
import com.sweflow.codingagent.context.ContextBuilder;
import com.sweflow.codingagent.producer.PullRequestEventProducer;
import com.sweflow.common.dto.ArtifactResponse;
import com.sweflow.common.enums.ArtifactType;
import com.sweflow.common.enums.PullRequestEventType;
import com.sweflow.common.events.CodingJobEvent;
import com.sweflow.common.events.PullRequestEvent;
import com.sweflow.storage.ArtifactStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class CodingAgentService {
    private final ContextBuilder contextBuilder;
    private final PullRequestEventProducer pullRequestEventProducer;

    public void execute(CodingJobEvent event) {
        CodingContext context = contextBuilder.build(event);
        log.info(
                "Coding context built. workflowId={}, designDocumentSize={}",
                context.workflowId(),
                context.designDocument().length()
        );

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
