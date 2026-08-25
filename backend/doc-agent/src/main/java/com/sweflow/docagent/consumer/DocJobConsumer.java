package com.sweflow.docagent.consumer;

import com.sweflow.common.constants.KafkaTopics;
import com.sweflow.common.events.DocJobEvent;
import com.sweflow.docagent.service.DocAgentDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DocJobConsumer {
    private final DocAgentDispatcher docAgentDispatcher;

    public DocJobConsumer(DocAgentDispatcher docAgentDispatcher) {
        this.docAgentDispatcher = docAgentDispatcher;
    }

    @KafkaListener(
            topics = KafkaTopics.DOC_JOBS,
            groupId = "doc-agent"
    )
    public void consume(DocJobEvent event) {
        log.info(
                "Received DocJobEvent. workflowId={}, workflowStepId = {}, issueId={}, stepType={}",
                event.workflowId(), event.workflowStepId(), event.issueId(), event.stepType()
        );

        this.docAgentDispatcher.handle(event);
    }
}