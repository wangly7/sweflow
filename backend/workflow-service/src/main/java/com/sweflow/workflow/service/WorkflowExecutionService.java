package com.sweflow.workflow.service;

import com.sweflow.workflow.entity.WorkflowExecutionEntity;
import com.sweflow.workflow.entity.WorkflowStepEntity;
import com.sweflow.workflow.entity.enums.WorkflowStatus;
import com.sweflow.workflow.entity.enums.WorkflowStepStatus;
import com.sweflow.common.enums.WorkflowStepType;
import com.sweflow.workflow.repository.WorkflowExecutionRepository;
import com.sweflow.workflow.repository.WorkflowStepRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkflowExecutionService {
    private final WorkflowExecutionRepository workflowExecutionRepository;
    private final WorkflowStepRepository workflowStepRepository;

    @Transactional
    public StartedWorkflow startWorkflow(UUID issueId) {
        UUID workflowExecutionId = UUID.randomUUID();

        WorkflowExecutionEntity execution = WorkflowExecutionEntity.builder()
                .id(workflowExecutionId)
                .issueId(issueId)
                .status(WorkflowStatus.RUNNING)
                .currentStep(WorkflowStepType.DESIGN_DOCUMENT)
                .build();
        WorkflowStepEntity step = WorkflowStepEntity.builder()
                .id(UUID.randomUUID())
                .workflowExecutionId(workflowExecutionId)
                .stepType(WorkflowStepType.DESIGN_DOCUMENT)
                .status(WorkflowStepStatus.RUNNING)
                .build();

        workflowExecutionRepository.save(execution);
        workflowStepRepository.save(step);

        return new StartedWorkflow(execution, step);
    }

    @Transactional
    public WorkflowStepEntity moveToNextStep(
            UUID workflowId,
            UUID currentStepId,
            WorkflowStepType nextStepType
    ) {
        WorkflowStepEntity currentStep = workflowStepRepository.findById(currentStepId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "workflow step not found" + currentStepId
                ));

        WorkflowExecutionEntity execution = workflowExecutionRepository.findById(workflowId)
                        .orElseThrow(() -> new IllegalArgumentException("Workflow execution not found" + workflowId));


        currentStep.setStatus(WorkflowStepStatus.COMPLETED);
        currentStep.setCompletedAt(OffsetDateTime.now());

        WorkflowStepEntity nextStep = WorkflowStepEntity.builder()
                .id(UUID.randomUUID())
                .workflowExecutionId(workflowId)
                .stepType(nextStepType)
                .status(WorkflowStepStatus.RUNNING)
                .build();

        execution.setCurrentStep(nextStepType);

        workflowStepRepository.save(currentStep);
        workflowStepRepository.save(nextStep);
        workflowExecutionRepository.save(execution);

        return nextStep;
    }
}
