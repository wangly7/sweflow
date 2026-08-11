package com.sweflow.aireviewworker.service;

import com.sweflow.aireviewworker.producer.ReviewResultProducer;
import com.sweflow.common.constants.KafkaTopics;
import com.sweflow.common.enums.ReviewVerdict;
import com.sweflow.common.events.ReviewJobEvent;
import com.sweflow.common.events.ReviewResultEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class AIReviewService {
    private final ReviewResultProducer reviewResultProducer;

    public void review(ReviewJobEvent event) {
        ReviewVerdict verdict = ReviewVerdict.APPROVED;

        ReviewResultEvent resultEvent = new ReviewResultEvent(
                UUID.randomUUID(),
                event.workflowId(),
                event.workflowStepId(),
                event.issueId(),
                event.repository(),
                event.prNumber(),
                verdict,
                "Mock review approved",
                null
        );

        reviewResultProducer.publish(resultEvent);
    }
}
