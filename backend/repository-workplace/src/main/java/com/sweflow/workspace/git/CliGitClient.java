package com.sweflow.workspace.git;

import com.sweflow.workspace.RepositorySource;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class CliGitClient implements GitClient {
    @Override
    public String resolveDefaultBranch(String repository) {
        String output = execute(
                null,
                "git",
                "ls-remote",
                "--symref",
                repository,
                "HEAD"
        );

        for (String line : output.lines().toList()) {
            if (line.startsWith("ref:")) {
                String reference = line.split("\\s+")[1];

                return reference.replace(
                        "refs/heads/",
                        ""
                );
            }
        }

        throw new IllegalStateException(
                "Unable to resolve default branch for repository: "
                + repository
        );
    }

    @Override
    public String resolveRemoteRevision(
            String repository,
            String branch
    ) {
        String output = execute(
                null,
                "git",
                "ls-remote",
                repository,
                "refs/heads/" + branch
        );
        if (output.isBlank()) {
            throw new IllegalStateException(
                    "Branch not found. repository="
                    + repository
                    + ", branch="
                    +branch
            );
        }
        return output.split("\\s+")[0];
    }

    @Override
    public void cloneRepository(
            String repository,
            Path targetPath
    ){
        throw new UnsupportedOperationException(
                "Not implement yet"
        );
    }

    @Override
    public void checkoutRevision(
            Path repositoryPath,
            String revision
    ){
        throw new UnsupportedOperationException(
                "Not implement yet"
        );
    }

    @Override
    public String getCurrentRevision(
            Path repositoryPath
    ){
        throw new UnsupportedOperationException(
                "Not implement yet"
        );
    }

    @Override
    public void createBranch(
            Path repositoryPath,
            String branchName
    ){
        throw new UnsupportedOperationException(
                "Not implement yet"
        );
    }

    private String execute(
            Path workingDirectory,
            String... command
    ) {
        try{
            ProcessBuilder processBuilder = new ProcessBuilder(command);

            if (workingDirectory != null) {
                processBuilder.directory(
                        workingDirectory.toFile()
                );
            }

            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            String output = new String(
                    process.getInputStream().readAllBytes()
            );

            int exitCode = process.waitFor();

            if (exitCode != 0) {
                throw  new IllegalStateException(
                        "Git command failed: "
                        + String.join(" ", command)
                        + "\n"
                        + output
                );
            }

            return  output.trim();

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Failed to execute Git command",
                    e
            );
        }
    }
}
