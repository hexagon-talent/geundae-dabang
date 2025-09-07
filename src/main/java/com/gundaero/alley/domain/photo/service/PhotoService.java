package com.gundaero.alley.domain.photo.service;

import com.gundaero.alley.domain.photo.entity.UserTourLocation;
import com.gundaero.alley.domain.photo.dao.UserTourLocationDAO;
import com.gundaero.alley.domain.photo.dto.response.PhotoCompleteResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gundaero.alley.domain.s3.service.S3Service;
import com.gundaero.alley.domain.photo.dto.response.PhotoListItemDTO;
import com.gundaero.alley.domain.photo.entity.PhotoRow;


import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final UserTourLocationDAO mapper;
    private final S3Service s3Service;

    @Transactional
    public PhotoCompleteResponseDTO complete(Long userId, Long locationId, String key) {
        if (userId == null || locationId == null || key == null || key.isBlank()) {
            throw new IllegalArgumentException("파라미터 오류");
        }
        UserTourLocation row = new UserTourLocation();
        row.setUserId(userId);
        row.setTourLocationId(locationId);
        row.setPhotoUrl(key);

        mapper.upsert(row);
        UserTourLocation saved = mapper.findOne(userId, locationId);

        return new PhotoCompleteResponseDTO(
                saved.getId(),
                saved.getTourLocationId(),
                saved.getPhotoUrl()
        );
    }


    public List<PhotoListItemDTO> listMyPhotos(Long userId) {
        List<PhotoRow> rows = mapper.findAllByUserWithTitle(userId);
        return rows.stream()
                .map(r -> new PhotoListItemDTO(
                        r.getTourLocationId(),
                        r.getLocationTitle(),
                        r.getPhotoUrl() == null ? null
                                : s3Service.presignedGetUrl(r.getPhotoUrl(), Duration.ofHours(1))
                ))
                .toList();
    }
}
