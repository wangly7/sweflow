package com.sweflow.workflow.controller;

import com.sweflow.common.dto.ArtifactResponse;
import com.sweflow.common.enums.ArtifactType;
import com.sweflow.workflow.entity.WorkflowArtifactEntity;
import com.sweflow.workflow.service.ArtifactService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/internal/workflows")
@RequiredArgsConstructor
public class ArtifactController {
    private final ArtifactService artifactService;

    @GetMapping("/{workflowId}/artifacts/latest")
    public ResponseEntity<ArtifactResponse> getLatestArtifact(
            @PathVariable UUID workflowId,
            @RequestParam ArtifactType type
    ) {
        WorkflowArtifactEntity artifact = artifactService.getLatestArtifact(
                workflowId,
                type
        );
        ArtifactResponse response = new ArtifactResponse(
                artifact.getArtifactType(),
                artifact.getStorageKey(),
                artifact.getVersion()
        );
        return ResponseEntity.ok(response);
    }
}
