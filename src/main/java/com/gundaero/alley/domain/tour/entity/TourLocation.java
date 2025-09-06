package com.gundaero.alley.domain.tour.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TourLocation {
    private Long id;
    private Long contentId;
    private String title;
    private String description;

    private Double latitude;
    private Double longitude;

    private String centerPointWkt;
    private String edgePointsWkt;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void setLatLng(Double lat, Double lon) {
        this.latitude = lat;
        this.longitude = lon;
        this.centerPointWkt = (lat != null && lon != null) ? ("POINT(" + lat + " " + lon + ")") : null;
    }

}
