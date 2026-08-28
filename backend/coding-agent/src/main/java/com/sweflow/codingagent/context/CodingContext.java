package com.sweflow.codingagent.context;

import java.util.UUID;

public record CodingContext(
        UUID workflowId,
        UUID workflowStepId,
        UUID issueId,
        String designDocument
) {
}
