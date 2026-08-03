package com.sweflow.workflow.consumer;

import com.sweflow.common.constants.KafkaTopics;
import com.sweflow.common.events.IssueCreatedEvent;
import com.sweflow.workflow.service.WorkflowOrchestrator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class IssueCreatedEventConsumer {
    private final WorkflowOrchestrator orchestrator;

    public IssueCreatedEventConsumer(WorkflowOrchestrator orchestrator){
        this.orchestrator = orchestrator;
    }

    @KafkaListener(topics = KafkaTopics.ISSUE_EVENTS, groupId = "workflow-service")
    public void consume(IssueCreatedEvent event) {
        log.info("Received IssueCreatedEvent. eventId={}, issueId={}, repository={}",
                event.eventId(),
                event.issueId(),
                event.repository()
        );
        orchestrator.handleIssueCreated(event);
    }
}
