package com.gundaero.alley.domain.map.dto.response;
import java.util.List;

public record MapLocationResponseDTO(
        Long locationId,
        String title,
        LatLng center,
        List<LatLng> polygon
) {}