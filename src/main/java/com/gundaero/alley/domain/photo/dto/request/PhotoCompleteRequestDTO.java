package com.gundaero.alley.domain.photo.dto.request;

public record PhotoCompleteRequestDTO (
        Long locationId,
        String key // S3 object key
) {}
