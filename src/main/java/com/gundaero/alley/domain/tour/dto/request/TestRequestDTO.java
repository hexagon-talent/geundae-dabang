package com.gundaero.alley.domain.tour.dto.request;

import java.util.List;

public record TestRequestDTO(
        Long userId,
        List<Long> tourLocationIds,
        String photoUrl
) {}