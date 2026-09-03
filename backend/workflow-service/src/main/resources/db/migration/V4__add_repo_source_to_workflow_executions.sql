ALTER TABLE workflow_executions
    ADD COLUMN base_branch VARCHAR(255),
    ADD COLUMN base_revision VARCHAR(255);