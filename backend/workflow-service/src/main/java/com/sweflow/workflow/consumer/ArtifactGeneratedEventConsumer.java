package com.sweflow.workflow.consumer;

import com.sweflow.common.constants.KafkaTopics;
import com.sweflow.common.events.ArtifactGeneratedEvent;
import com.sweflow.workflow.service.ArtifactService;
import com.sweflow.workflow.service.WorkflowOrchestrator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ArtifactGeneratedEventConsumer {
    private final WorkflowOrchestrator orchestrator;
    private final ArtifactService artifactService;

    @KafkaListener(
            topics = KafkaTopics.ARTIFACT_EVENTS,
            groupId = "workflow-service"
    )
    public void consume(ArtifactGeneratedEvent event) {
        log.info(
                "Received ArtifactGeneratedEvent. artifactId={}, issueId={}, artifactType={}",
                event.artifactId(),
                event.issueId(),
                event.artifactType()
        );
        artifactService.saveArtifact(event);
        orchestrator.handleArtifactGenerated(event);
    }
}
