package com.sweflow.codingagent.consumer;


import com.sweflow.codingagent.service.CodingAgentService;
import com.sweflow.common.constants.KafkaTopics;
import com.sweflow.common.events.CodingJobEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CodingJobConsumer {
    public final CodingAgentService codingAgentService;

    @KafkaListener(
            topics = KafkaTopics.CODING_JOBS,
            groupId = "coding-agent"
    )
    public void consume(CodingJobEvent event) {
        log.info(
                "Received CodingJobEvent. workflowId={}. workflowStepId={}, issueId={}",
                event.workflowId(),
                event.workflowStepId(),
                event.issueId()
        );
        codingAgentService.execute(event);
    }

}
