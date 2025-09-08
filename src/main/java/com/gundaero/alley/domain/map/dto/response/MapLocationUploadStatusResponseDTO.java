package com.gundaero.alley.domain.map.dto.response;

public record MapLocationUploadStatusResponseDTO(
        Long locationId,
        String title,
        boolean uploaded
) {}