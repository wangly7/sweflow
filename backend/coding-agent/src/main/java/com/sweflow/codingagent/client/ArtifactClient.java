package com.sweflow.codingagent.client;

import com.sweflow.common.dto.ArtifactResponse;
import com.sweflow.common.enums.ArtifactType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ArtifactClient {
    private final RestClient restClient;

    @Value("${services.workflow-service.base-url}")
    private String baseUrl;

    public ArtifactResponse getLatestArtifact(
            UUID workflowId,
            ArtifactType artifactType
    ) {
        return restClient.get()
                .uri(
                        baseUrl
                        + "/internal/workflows/{workflowId}/artifacts/latest?type={type}",
                        workflowId,
                        artifactType.name()
                )
                .retrieve()
                .body(ArtifactResponse.class);
    }
}
