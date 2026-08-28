package com.sweflow.codingagent.context;

import com.sweflow.codingagent.client.ArtifactClient;
import com.sweflow.common.dto.ArtifactResponse;
import com.sweflow.common.enums.ArtifactType;
import com.sweflow.common.events.CodingJobEvent;
import com.sweflow.storage.ArtifactStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class ContextBuilder {
    private final ArtifactStorage artifactStorage;
    private final ArtifactClient artifactClient;

    public CodingContext build(CodingJobEvent event) {
        ArtifactResponse designArtifact =
                artifactClient.getLatestArtifact(
                        event.workflowId(),
                        ArtifactType.DESIGN_DOC
                );

        byte[] content =
                artifactStorage.download(
                        designArtifact.storageKey()
                );

        String designDocument =
                new String(
                        content,
                        StandardCharsets.UTF_8
                );

        return new CodingContext(
                event.workflowId(),
                event.workflowStepId(),
                event.issueId(),
                designDocument
        );
    }
}
