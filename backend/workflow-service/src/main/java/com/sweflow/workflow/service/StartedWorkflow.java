package com.sweflow.workflow.service;

import com.sweflow.workflow.entity.WorkflowExecutionEntity;
import com.sweflow.workflow.entity.WorkflowStepEntity;

public record StartedWorkflow(
        WorkflowExecutionEntity execution,
        WorkflowStepEntity step
) {
}
