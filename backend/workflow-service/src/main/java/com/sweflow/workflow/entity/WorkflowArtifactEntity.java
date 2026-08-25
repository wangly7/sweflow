package com.sweflow.workflow.entity;

import com.sweflow.common.enums.ArtifactType;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name="workflow_artifacts")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class WorkflowArtifactEntity {
    @Id
    private UUID id;

    @Column(name = "workflow_id", nullable = false)
    private UUID workflowId;

    @Column(name = "workflow_step_id", nullable = false)
    private UUID workflowStepId;

    @Enumerated(EnumType.STRING)
    @Column(name = "artifact_type", nullable = false, length = 50)
    private ArtifactType artifactType;

    @Column(nullable = false)
    private Integer version;

    @Column(name = "storage_key", nullable = false, length = 512)
    private String storageKey;

    @Column(name = "created_by_agent", nullable = false, length = 100)
    private String createdByAgent;

    @Column(
            name = "created_at",
            insertable = false,
            updatable = false
    )
    private OffsetDateTime createdAt;
}
