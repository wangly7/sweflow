package com.sweflow.aireviewworker.service;

import com.sweflow.aireviewworker.producer.ReviewResultProducer;
import com.sweflow.common.enums.ReviewVerdict;
import com.sweflow.common.events.ReviewJobEvent;
import com.sweflow.common.events.ReviewResultEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class AIReviewService {
    private final ReviewResultProducer reviewResultProducer;

    @Value("${mock.review-verdict}")
    private ReviewVerdict mockVerdict;

    public void review(ReviewJobEvent event) {

        ReviewResultEvent resultEvent = new ReviewResultEvent(
                UUID.randomUUID(),
                event.workflowId(),
                event.workflowStepId(),
                event.issueId(),
                event.prNumber(),
                mockVerdict,
                "Mock review completed",
                mockVerdict == ReviewVerdict.APPROVED ? null : "Feedback"
        );

        reviewResultProducer.publish(resultEvent);
    }
}
