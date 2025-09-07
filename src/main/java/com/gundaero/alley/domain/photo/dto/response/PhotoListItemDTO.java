package com.gundaero.alley.domain.photo.dto.response;

public record PhotoListItemDTO(
        Long locationId,
        String locationName,
        String url
) {}
