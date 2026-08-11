package com.sweflow.aireviewworker.producer;

import com.sweflow.common.constants.KafkaTopics;
import com.sweflow.common.events.ReviewResultEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewResultProducer {
    private final KafkaTemplate<String, ReviewResultEvent> kafkaTemplate;

    public void publish(ReviewResultEvent event) {
        log.info(
                "Publishing ReviewResultEvent. eventId={}, workflowId={}, workflowStepId={}, issueId={}, verdict={}",
                event.eventId(),
                event.workflowId(),
                event.workflowStepId(),
                event.issueId(),
                event.reviewVerdict()
        );
        kafkaTemplate.send(
                KafkaTopics.REVIEW_RESULTS,
                event.issueId().toString(),
                event
        );
    }

}
