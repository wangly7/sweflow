package com.sweflow.workflow.service;

import com.sweflow.common.events.ArtifactGeneratedEvent;
import com.sweflow.workflow.entity.WorkflowArtifactEntity;
import com.sweflow.workflow.repository.WorkflowArtifactRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArtifactService {
    private final WorkflowArtifactRepository workflowArtifactRepository;

    @Transactional
    public WorkflowArtifactEntity saveArtifact(
            ArtifactGeneratedEvent event
    ) {
        int nextVersion = workflowArtifactRepository
                .findMaxVersion(
                        event.workflowId(),
                        event.artifactType()
                ).orElse(0) + 1;

        WorkflowArtifactEntity artifact =
                WorkflowArtifactEntity.builder()
                        .id(UUID.randomUUID())
                        .workflowId(event.workflowId())
                        .workflowStepId(event.workflowStepId())
                        .artifactType(event.artifactType())
                        .storageKey(event.storagePath())
                        .version(nextVersion)
                        .createdByAgent(event.createdByAgent())
                        .build();

        return workflowArtifactRepository.save(artifact);
    }
}
