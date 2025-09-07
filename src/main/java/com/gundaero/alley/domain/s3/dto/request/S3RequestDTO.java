package com.gundaero.alley.domain.s3.dto.request;

public record S3RequestDTO(
        String type,
        String originalFilename,
        String contentType
) {}
