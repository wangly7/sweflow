package com.sweflow.workflow.repository;

import com.sweflow.workflow.entity.WorkflowExecutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WorkflowExecutionRepository extends JpaRepository<WorkflowExecutionEntity, UUID>{
    Optional<WorkflowExecutionEntity> findByIssueId(UUID issueId);
}
