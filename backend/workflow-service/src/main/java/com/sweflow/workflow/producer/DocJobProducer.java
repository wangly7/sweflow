package com.sweflow.workflow.producer;

import com.sweflow.common.constants.KafkaTopics;
import com.sweflow.common.events.DocJobEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DocJobProducer {
    private final KafkaTemplate<String, DocJobEvent> kafkaTemplate;

    public DocJobProducer(KafkaTemplate<String, DocJobEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(DocJobEvent event) {
        log.info("Publishing DocJobEvent. topic={}, jobId={}, issueId={}",
                KafkaTopics.DOC_JOBS,
                event.jobId(),
                event.issueId()
        );
        kafkaTemplate.send(
                KafkaTopics.DOC_JOBS,
                event.jobId(),
                event
        );
    }
}
