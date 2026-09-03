package com.sweflow.workspace;

import java.util.UUID;

public interface RepositoryWorkplaceManager {
    RepositorySource resolveSource(
            String repository,
            String baseBranch
    );

    RepositoryWorkspace prepare(
            UUID workflowId,
            RepositorySource source
    );

    RepositoryWorkspace get(UUID workflowId);

    void cleanup(UUID workflowId);
}
