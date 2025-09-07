package com.gundaero.alley.domain.photo.dto.request;

public record PhotoUploadRequestDTO (
        Long locationId,
        String originalFilename,
        String contentType
) {}
