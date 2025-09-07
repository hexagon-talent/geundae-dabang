package com.gundaero.alley.domain.s3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.net.URL;
import java.time.Duration;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3PresignService {

    private final S3Presigner presigner;

    @Value("${aws.s3.bucket}")
    private String bucket;

    private static final Set<String> ALLOWED_CT = Set.of("image/jpeg", "image/png");

    public String buildKey(String type, String originalFilename) {
        String t = (type == null || type.isBlank()) ? "uploads" : type.trim();
        String name = (originalFilename == null || originalFilename.isBlank()) ? "unnamed" : originalFilename;
        return t + "/" + UUID.randomUUID() + "_" + name;
    }

    public URL presignPut(String key, String contentType, Duration ttl) {
        if (key == null || key.isBlank()) throw new IllegalArgumentException("key가 비어있습니다.");
        if (contentType == null || !ALLOWED_CT.contains(contentType)) {
            throw new IllegalArgumentException("지원하지 않는 contentType");
        }

        PutObjectRequest put = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(contentType)
                .build();

        PutObjectPresignRequest preq = PutObjectPresignRequest.builder()
                .signatureDuration(ttl)
                .putObjectRequest(put)
                .build();

        PresignedPutObjectRequest signed = presigner.presignPutObject(preq);
        return signed.url();
    }
}