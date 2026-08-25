CREATE TABLE workflow_artifacts (
    id UUID PRIMARY KEY,
    workflow_id UUID  NOT NULL,
    workflow_step_id UUID NOT NULL,
    artifact_type VARCHAR(50) NOT NULL,
    version INTEGER NOT NULL,
    storage_key VARCHAR(512) NOT NULL,
    supersedes_artifact_id UUID,
    created_by_agent VARCHAR(50) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_artifact_workflow
        FOREIGN KEY (workflow_id)
        REFERENCES workflow_executions(id),

    CONSTRAINT fk_artifact_step
        FOREIGN KEY (workflow_step_id)
        REFERENCES workflow_steps(id),

    CONSTRAINT fk_supersedes_artifact
        FOREIGN KEY (supersedes_artifact_id)
        REFERENCES workflow_artifacts(id)
);

CREATE INDEX idx_workflow_artifacts_workflow_type
ON workflow_artifacts(workflow_id, artifact_type);

CREATE UNIQUE INDEX uq_workflow_artifacts_version
ON workflow_artifacts(workflow_id, artifact_type, version);