package com.gundaero.alley.domain.tour.dto.request;

import jakarta.validation.constraints.*;

public record LocationBasedListRequestDTO(
        @NotNull
        @DecimalMin(value = "-180.0", inclusive = true, message = "mapX(lon)은 -180 이상이어야 합니다.")
        @DecimalMax(value = "180.0",  inclusive = true, message = "mapX(lon)은 180 이하이어야 합니다.")
        Double mapX,

        @NotNull
        @DecimalMin(value = "-90.0", inclusive = true, message = "mapY(lat)은 -90 이상이어야 합니다.")
        @DecimalMax(value = "90.0",  inclusive = true, message = "mapY(lat)은 90 이하이어야 합니다.")
        Double mapY,

        @Min(1) @Max(20000) Integer radius,
        Integer pageNo,
        Integer numOfRows
) {}
