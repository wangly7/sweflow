package com.sweflow.workflow.repository;

import com.sweflow.common.enums.ArtifactType;
import com.sweflow.workflow.entity.WorkflowArtifactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface WorkflowArtifactRepository
        extends JpaRepository<WorkflowArtifactEntity, UUID> {
    Optional<WorkflowArtifactEntity>
    findTopByWorkflowIdAndArtifactTypeOrderByVersionDesc(
            UUID workflowId,
            ArtifactType artifactType
    );

    @Query("""
    SELECT MAX(a.version)
    FROM WorkflowArtifactEntity a
    WHERE a.workflowId = :workflowId
        AND a.artifactType = :artifactType
    """)
    Optional<Integer>
    findMaxVersion(
            @Param("workflowId") UUID workflowId,
            @Param("artifactType") ArtifactType artifactType
    );
}
