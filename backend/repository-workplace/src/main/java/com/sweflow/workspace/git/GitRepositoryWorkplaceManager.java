package com.sweflow.workspace.git;

import com.sweflow.workspace.RepositorySource;
import com.sweflow.workspace.RepositoryWorkplaceManager;
import com.sweflow.workspace.RepositoryWorkspace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GitRepositoryWorkplaceManager implements RepositoryWorkplaceManager {
    private final GitClient gitClient;

    @Override
    public RepositorySource resolveSource(
            String repository,
            String branch
    ) {
        String basedBranch =
                branch != null && !branch.isBlank()
                ? branch : gitClient.resolveDefaultBranch(repository);

        String basedRevision =
                gitClient.resolveRemoteRevision(
                        repository,
                        basedBranch
                );
        return new RepositorySource(
                repository,
                basedBranch,
                basedRevision
        );
    }

    @Override
    public RepositoryWorkspace prepare(
            UUID workflowId,
            RepositorySource source
    ) {
        throw new UnsupportedOperationException(
                "Not implemented yet"
        );
    }

    @Override
    public RepositoryWorkspace get(UUID workflowId) {
        throw new UnsupportedOperationException(
                "Not implemented yet"
        );
    }

    @Override
    public void cleanup(UUID workflowId) {
        throw new UnsupportedOperationException(
                "Not implemented yet"
        );
    }
}
