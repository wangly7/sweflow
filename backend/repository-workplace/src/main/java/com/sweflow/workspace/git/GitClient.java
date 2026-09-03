package com.sweflow.workspace.git;

import java.nio.file.Path;

public interface GitClient {
    String resolveDefaultBranch(String repository);

    String resolveRemoteRevision(
            String repository,
            String branch
    );

    void cloneRepository(
            String repository,
            Path targetPath
    );

    void checkoutRevision(
            Path repositoryPath,
            String revision
    );

    String getCurrentRevision(
            Path repositoryPath
    );

    void createBranch(
            Path repositoryPath,
            String branchName
    );
}
