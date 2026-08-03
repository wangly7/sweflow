package com.sweflow.codingagent.producer;

import com.sweflow.common.constants.KafkaTopics;
import com.sweflow.common.events.PullRequestEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PullRequestEventProducer {
    private final KafkaTemplate<String, PullRequestEvent> kafkaTemplate;

    public void publish(PullRequestEvent event) {
        log.info(
                "Publishing PullRequestEvent. eventId={}, issueId={}, prNumber={}, source={}",
                event.eventId(),
                event.issueId(),
                event.prNumber(),
                event.source()
        );
        kafkaTemplate.send(KafkaTopics.PR_EVENTS, event.issueId(), event);
    }
}
