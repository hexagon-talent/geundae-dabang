package com.gundaero.alley.domain.tour.dto.response.record;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TourApiHeader(String resultCode, String resultMsg) {
}
