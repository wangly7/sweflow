package com.sweflow.docagent.client;

import com.sweflow.common.dto.IssueResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class IssueClient {
    private final RestClient restClient;

    @Value("${services.issue-service.base-url}")
    private String baseUrl;

    public IssueResponse getIssue(UUID issueId) {
        return restClient.get()
                .uri(
                        baseUrl + "/issues/{issueId}",
                        issueId
                ).retrieve().body(IssueResponse.class);
    }
}
