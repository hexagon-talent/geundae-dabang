package com.gundaero.alley.domain.s3.dto.response;

public record S3ResponseDTO(
        String uploadUrl,
        String key
) {}
