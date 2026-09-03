package com.sweflow.workspace;

public record RepositorySource(
        String repository,
        String baseBranch,
        String baseRevision
) {
}
