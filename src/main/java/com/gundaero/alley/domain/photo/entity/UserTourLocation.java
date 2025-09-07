package com.gundaero.alley.domain.photo.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserTourLocation {
    private Long id;
    private Long userId;
    private Long tourLocationId;
    private String photoUrl; // S3 key 저장
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
