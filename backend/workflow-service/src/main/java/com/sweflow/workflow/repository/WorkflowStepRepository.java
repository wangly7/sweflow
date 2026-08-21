package com.sweflow.workflow.repository;

import com.sweflow.common.enums.WorkflowStepType;
import com.sweflow.workflow.entity.WorkflowStepEntity;
import com.sweflow.workflow.entity.enums.WorkflowStepStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkflowStepRepository extends JpaRepository<WorkflowStepEntity, UUID> {

    List<WorkflowStepEntity> findByWorkflowExecutionId(UUID workflowExecutionId);

    List<WorkflowStepEntity> findByWorkflowExecutionIdAndStatus(
            UUID workflowExecutionId,
            WorkflowStepStatus status
    );

    Optional<WorkflowStepEntity> findTopByWorkflowExecutionIdAndStepTypeOrderByStartedAtDesc(
            UUID workflowExecutionId,
            WorkflowStepType stepType
    );
}
