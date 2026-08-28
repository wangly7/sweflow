package com.sweflow.common.dto;

import com.sweflow.common.enums.ArtifactType;

import java.util.UUID;

public record ArtifactResponse(
        ArtifactType artifactType,
        String storageKey,
        Integer version
) {
}