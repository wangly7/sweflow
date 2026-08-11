package com.sweflow.aireviewworker.consumer;

import com.sweflow.aireviewworker.service.AIReviewService;
import com.sweflow.common.constants.KafkaTopics;
import com.sweflow.common.events.ReviewJobEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ReviewEventConsumer {
    private final AIReviewService aiReviewService;

    @KafkaListener(
            topics = KafkaTopics.REVIEW_JOBS,
            groupId = "ai-review-worker"
    )
    public void consume(ReviewJobEvent event) {
        log.info(
                "Received ReviewJobEvent. eventId={}, workflowId={}, workflowStepId={}, issueId={}, prNumber={}",
                event.eventId(),
                event.workflowId(),
                event.workflowStepId(),
                event.issueId(),
                event.prNumber()
        );

        aiReviewService.review(event);
    }
}
