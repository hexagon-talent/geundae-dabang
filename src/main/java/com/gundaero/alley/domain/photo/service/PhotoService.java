package com.gundaero.alley.domain.photo.service;

import com.gundaero.alley.domain.photo.entity.UserTourLocation;
import com.gundaero.alley.domain.photo.dao.UserTourLocationDAO;
import com.gundaero.alley.domain.photo.dto.response.PhotoCompleteResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final UserTourLocationDAO mapper;

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
}
