package com.gundaero.alley.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.net.URL;
import java.time.Duration;

@Service
public class S3PresignService {

    private final String bucket;
    private final Region region;

    public S3PresignService(
            @Value("${aws.s3.bucket}") String bucket,
            @Value("${aws.s3.region}") String region
    ) {
        this.bucket = bucket;
        this.region = Region.of(region);
    }

    private S3Presigner presigner() {
        return S3Presigner.builder()
                .region(region)
                .credentialsProvider(DefaultCredentialsProvider.create()) // EC2 IAM Role 사용
                .serviceConfiguration(S3Configuration.builder().build())
                .build();
    }

    public URL presignPut(String key, String contentType, Duration ttl) {
        PutObjectRequest putReq = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(contentType)
                .build();

        PutObjectPresignRequest presignReq = PutObjectPresignRequest.builder()
                .signatureDuration(ttl)
                .putObjectRequest(putReq)
                .build();

        try (S3Presigner p = presigner()) {
            return p.presignPutObject(presignReq).url();
        }
    }

    public URL presignGet(String key, Duration ttl) {
        GetObjectRequest getReq = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        GetObjectPresignRequest presignReq = GetObjectPresignRequest.builder()
                .signatureDuration(ttl)
                .getObjectRequest(getReq)
                .build();

        try (S3Presigner p = presigner()) {
            return p.presignGetObject(presignReq).url();
        }
    }
}
