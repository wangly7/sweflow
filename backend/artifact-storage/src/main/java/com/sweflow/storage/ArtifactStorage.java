package com.sweflow.storage;

public interface ArtifactStorage {
    String upload(
            String objectKey,
            byte[] content,
            String contentType
    );

    byte[] download(String objectKey);

    void delete(String objectKey);
}
