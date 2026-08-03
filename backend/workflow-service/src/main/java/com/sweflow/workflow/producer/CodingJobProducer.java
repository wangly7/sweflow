package com.sweflow.workflow.producer;

import com.sweflow.common.constants.KafkaTopics;
import com.sweflow.common.events.CodingJobEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CodingJobProducer {
    private final KafkaTemplate<String, CodingJobEvent> kafkaTemplate;

    public CodingJobProducer(KafkaTemplate<String, CodingJobEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(CodingJobEvent event) {
        kafkaTemplate.send(
                KafkaTopics.CODING_JOBS,
                event.issueId(),
                event
        );
    }
}
