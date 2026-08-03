package com.sweflow.workflow.service;

import com.sweflow.common.enums.WorkflowStepType;
import com.sweflow.workflow.entity.WorkflowStepEntity;
import com.sweflow.workflow.producer.CodingJobProducer;
import com.sweflow.workflow.producer.DocJobProducer;
import com.sweflow.common.events.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkflowOrchestrator {
    public final WorkflowExecutionService workflowExecutionService;
    public final DocJobProducer docJobProducer;
    public final CodingJobProducer codingJobProducer;

    public void handleIssueCreated(IssueCreatedEvent event) {
        StartedWorkflow workflow = workflowExecutionService.startWorkflow(event.issueId());
        DocJobEvent job = new DocJobEvent(
                UUID.randomUUID(),
                workflow.execution().getId(),
                workflow.step().getId(),
                event.issueId(),
                event.title(),
                event.description(),
                event.repository(),
                workflow.step().getStepType()
        );
        this.docJobProducer.publish(job);
    }

    public void handleArtifactGenerated(ArtifactGeneratedEvent event) {
        WorkflowStepEntity codingStep = workflowExecutionService.moveToNextStep(
                event.workflowId(),
                event.workflowStepId(),
                WorkflowStepType.CODING
        );

        String targetBranch = "ai/" + event.issueId();

        CodingJobEvent codingJobEvent = new CodingJobEvent(
                UUID.randomUUID(),
                event.workflowId(),
                codingStep.getId(),
                event.issueId(),
                event.artifactId(),
                event.repository(),
                event.storagePath(),
                targetBranch
        );
        log.info(
                "Dispatching coding job. eventId={},  workflowId={}, workflowStepId={}, issueId={}",
                codingJobEvent.eventId(),
                codingJobEvent.workflowId(),
                codingJobEvent.workflowStepId(),
                codingJobEvent.issueId()
        );
        codingJobProducer.publish(codingJobEvent);
    }

    public void handlePullRequestEvent(PullRequestEvent event) {

    }
}
