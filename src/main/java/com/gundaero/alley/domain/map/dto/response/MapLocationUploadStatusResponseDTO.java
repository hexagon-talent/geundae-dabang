package com.gundaero.alley.domain.map.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record MapLocationUploadStatusResponseDTO(
        Long locationId,
        String title,
        boolean uploaded
) {}