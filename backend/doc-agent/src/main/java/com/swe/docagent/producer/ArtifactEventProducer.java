package com.swe.docagent.producer;

import com.sweflow.common.constants.KafkaTopics;
import com.sweflow.common.events.ArtifactGeneratedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ArtifactEventProducer {
    private final KafkaTemplate<String, ArtifactGeneratedEvent> kafkaTemplate;

    public ArtifactEventProducer(KafkaTemplate<String, ArtifactGeneratedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(ArtifactGeneratedEvent event) {
        kafkaTemplate.send(
                KafkaTopics.ARTIFACT_EVENTS,
                event.issueId().toString(),
                event
        );
    }
}
