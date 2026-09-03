package com.sweflow.workspace;

import java.nio.file.Path;
import java.util.UUID;

public record RepositoryWorkspace(
        UUID workflowId,
        String repository,
        String baseBranch,
        String baseVersion,
        Path rootPath
) {
}
