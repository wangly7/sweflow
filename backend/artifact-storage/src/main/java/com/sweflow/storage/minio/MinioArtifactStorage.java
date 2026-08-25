package com.sweflow.storage.minio;

import com.sweflow.storage.ArtifactStorage;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class MinioArtifactStorage implements ArtifactStorage {
    private final MinioClient minioClient;

    @Value("${artifact-storage.minio.bucket}")
    private String bucket;

    @Override
    public String upload(
            String objectKey,
            byte[] content,
            String contentType
    ){
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectKey)
                            .stream(
                                    new ByteArrayInputStream(content),
                                    content.length,
                                    -1
                            )
                            .contentType(contentType)
                            .build()
            );

            return objectKey;
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to upload artifact: " + objectKey,
                    e
            );
        }

    }

    @Override
    public byte[] download(String objectKey){
        try (InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucket)
                        .object(objectKey)
                        .build()
        )) {
            return stream.readAllBytes();

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to download artifact: " + objectKey,
                    e
            );
        }
    }

    @Override
    public void delete(String objectKey) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectKey)
                            .build()
            );

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to delete artifact: " + objectKey,
                    e
            );
        }
    }
}
