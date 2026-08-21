package com.sweflow.workflow.service;

import com.sweflow.common.enums.WorkflowStepType;
import com.sweflow.workflow.entity.WorkflowStepEntity;
import com.sweflow.workflow.producer.CodingJobProducer;
import com.sweflow.workflow.producer.DocJobProducer;
import com.sweflow.common.events.*;
import com.sweflow.workflow.producer.ReviewJobProducer;
import com.sweflow.workflow.repository.WorkflowStepRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkflowOrchestrator {
    public final WorkflowExecutionService workflowExecutionService;
    public final DocJobProducer docJobProducer;
    public final CodingJobProducer codingJobProducer;
    public final ReviewJobProducer reviewJobProducer;
    private final WorkflowStepRepository workflowStepRepository;

    public void handleIssueCreated(IssueCreatedEvent event) {
        StartedWorkflow workflow = workflowExecutionService.startWorkflow(event.issueId());
        DocJobEvent job = new DocJobEvent(
                UUID.randomUUID(),
                workflow.execution().getId(),
                workflow.step().getId(),
                event.issueId(),
                workflow.step().getStepType(),
                null
        );
        this.docJobProducer.publish(job);
    }

    public void handleArtifactGenerated(ArtifactGeneratedEvent event) {
        Map<String, Object> config = Map.of(
                "targetBranch", "ai/issue-"+event.issueId()
        );
        WorkflowStepEntity codingStep = workflowExecutionService.moveToNextStep(
                event.workflowId(),
                event.workflowStepId(),
                WorkflowStepType.CODING,
                config
        );

        CodingJobEvent codingJobEvent = new CodingJobEvent(
                UUID.randomUUID(),
                event.workflowId(),
                codingStep.getId(),
                event.issueId(),
                null
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
        WorkflowStepEntity reviewStep = workflowExecutionService.moveToNextStep(
                event.workflowId(),
                event.workflowStepId(),
                WorkflowStepType.AI_REVIEW,
                Map.of()
        );

        ReviewJobEvent reviewJobEvent = new ReviewJobEvent(
                UUID.randomUUID(),
                event.workflowId(),
                reviewStep.getId(),
                event.issueId(),
                event.prNumber()
        );

        reviewJobProducer.publish(reviewJobEvent);


    }

    public void handleReviewResult(ReviewResultEvent event) {
        switch (event.reviewVerdict()) {
            case APPROVED -> {
                workflowExecutionService.completeWorkflow(
                        event.workflowId(),
                        event.workflowStepId()
                );
            }
            case CODE_CHANGES_REQUESTED ->  {
                WorkflowStepEntity previous = workflowStepRepository
                        .findTopByWorkflowExecutionIdAndStepTypeOrderByStartedAtDesc(
                                event.workflowId(),
                                WorkflowStepType.CODING
                ).orElseThrow(() -> new IllegalStateException(
                        "previous coding step not found" + event.workflowId()
                ));
                String targetBranch = previous.getExecutionConfig().get("targetBranch").toString();
                Map<String, Object> config = Map.of(
                        "targetBranch", targetBranch
                );
                WorkflowStepEntity codingStep = workflowExecutionService.moveToNextStep(
                        event.workflowId(),
                        event.workflowStepId(),
                        WorkflowStepType.CODING,
                        config
                );

                CodingJobEvent codingJobEvent = new CodingJobEvent(
                        UUID.randomUUID(),
                        event.workflowId(),
                        codingStep.getId(),
                        event.issueId(),
                        event.feedback()
                );

                codingJobProducer.publish(codingJobEvent);
            }
            case DESIGN_CHANGES_REQUESTED -> {
                WorkflowStepEntity designStep = workflowExecutionService.moveToNextStep(
                        event.workflowId(),
                        event.workflowStepId(),
                        WorkflowStepType.DESIGN_DOCUMENT,
                        Map.of()
                );

                DocJobEvent docJobEvent = new DocJobEvent(
                        UUID.randomUUID(),
                        event.workflowId(),
                        designStep.getId(),
                        event.issueId(),
                        WorkflowStepType.DESIGN_DOCUMENT,
                        event.feedback()
                );
                docJobProducer.publish(docJobEvent);
            }
            case FAILED -> {
                workflowExecutionService.failWorkflow(
                        event.workflowId(),
                        event.workflowStepId()
                );
            }
        }
    }
}
