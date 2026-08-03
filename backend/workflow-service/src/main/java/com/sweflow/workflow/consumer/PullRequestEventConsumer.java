package com.sweflow.workflow.consumer;

import com.sweflow.common.constants.KafkaTopics;
import com.sweflow.common.events.PullRequestEvent;
import com.sweflow.workflow.service.WorkflowOrchestrator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PullRequestEventConsumer {
    private final WorkflowOrchestrator orchestrator;

    @KafkaListener(
            topics = KafkaTopics.PR_EVENTS,
            groupId = "workflow-service"
    )
    public void consume(PullRequestEvent event) {
    }
}
