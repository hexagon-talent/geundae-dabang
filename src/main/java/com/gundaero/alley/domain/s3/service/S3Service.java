package com.gundaero.alley.domain.s3.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {

    private final S3Client s3Client;
    private final S3Presigner presigner;

    @Value("${aws.s3.bucket}")
    private String bucket;

    private final Cache<String, String> urlCache = Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofMinutes(50))
            .maximumSize(1_000)
            .recordStats()
            .build();

    public String presignedGetUrl(String key, Duration ttl) {
        if (key == null || key.isBlank()) throw new IllegalArgumentException("key가 비었습니다.");

        String cached = urlCache.getIfPresent(key);
        if (cached != null) {
            log.debug("S3 GET URL cache hit: {}", key);
            return cached;
        }

        GetObjectRequest get = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        GetObjectPresignRequest preq = GetObjectPresignRequest.builder()
                .signatureDuration(ttl)
                .getObjectRequest(get)
                .build();

        String url = presigner.presignGetObject(preq).url().toString();
        urlCache.put(key, url);
        return url;
    }
}