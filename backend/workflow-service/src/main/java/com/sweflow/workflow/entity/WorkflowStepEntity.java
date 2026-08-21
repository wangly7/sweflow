package com.sweflow.workflow.entity;

import com.sweflow.workflow.entity.enums.WorkflowStepStatus;
import com.sweflow.common.enums.WorkflowStepType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "workflow_steps")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkflowStepEntity {
    @Id
    private UUID id;

    @Column(name = "workflow_execution_id", nullable = false)
    private UUID workflowExecutionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "step_type", nullable = false, length = 50)
    private WorkflowStepType stepType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private WorkflowStepStatus status;

    @Column(
            name = "started_at",
            insertable = false,
            updatable = false
    )
    private OffsetDateTime startedAt;

    @Column(name = "completed_at")
    private OffsetDateTime completedAt;

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "execution_config", columnDefinition = "jsonb")
    private Map<String, Object> executionConfig;
}


