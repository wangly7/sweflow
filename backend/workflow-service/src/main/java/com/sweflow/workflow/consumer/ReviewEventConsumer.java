package com.sweflow.workflow.consumer;

import com.sweflow.common.constants.KafkaTopics;
import com.sweflow.common.events.ReviewResultEvent;
import com.sweflow.workflow.service.WorkflowOrchestrator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewEventConsumer {
    private final WorkflowOrchestrator workflowOrchestrator;

    @KafkaListener(
            topics = KafkaTopics.REVIEW_RESULTS,
            groupId = "workflow-service"
    )
    public void consume(ReviewResultEvent event) {
        log.info(
                "Received ReviewResultEvent. eventId={}, workflowId={}, workflowStepId={}, issueId={}, verdict={}",
                event.eventId(),
                event.workflowId(),
                event.workflowStepId(),
                event.issueId(),
                event.reviewVerdict()
        );
        workflowOrchestrator.handleReviewResult(event);
    }
}
