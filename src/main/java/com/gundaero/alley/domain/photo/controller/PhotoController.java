package com.gundaero.alley.domain.photo.controller;

import com.gundaero.alley.common.ApiResponse;
import com.gundaero.alley.domain.photo.dto.request.PhotoCompleteRequestDTO;
import com.gundaero.alley.domain.photo.dto.request.PhotoUploadRequestDTO;
import com.gundaero.alley.domain.photo.dto.response.PhotoCompleteResponseDTO;
import com.gundaero.alley.domain.photo.dto.response.PhotoListItemDTO;
import com.gundaero.alley.domain.photo.dto.response.PhotoUploadResponseDTO;
import com.gundaero.alley.domain.photo.service.PhotoService;
import com.gundaero.alley.domain.s3.service.S3PresignService;
import com.gundaero.alley.domain.auth.entity.CustomUserDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/photos")
@RequiredArgsConstructor
@Tag(name = "Photo", description = "사진 관련")
public class PhotoController implements PhotoControllerDocs {

    private final S3PresignService presignService;
    private final PhotoService photoService;

    private String buildPhotoKey(Long userId, Long locationId, String originalFilename) {
        String safe = originalFilename == null ? "image" : originalFilename.replaceAll("[\\s]+", "_");
        return "photos/%d/%d/%s_%s".formatted(userId, locationId, UUID.randomUUID(), safe);
    }

    @PostMapping("/presigned")
    public ResponseEntity<ApiResponse<PhotoUploadResponseDTO>> uploadPresign(
            @AuthenticationPrincipal CustomUserDetail me,
            @RequestBody PhotoUploadRequestDTO req
    ) {
        Long userId = me.getUser().getId();
        String key = buildPhotoKey(userId, req.locationId(), req.originalFilename());

        URL url = presignService.presignPut(key, req.contentType(), Duration.ofMinutes(5));
        PhotoUploadResponseDTO payload = new PhotoUploadResponseDTO(url.toString(), key);

        return ResponseEntity.ok(ApiResponse.success("presign", payload));
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<PhotoCompleteResponseDTO>> complete(
            @AuthenticationPrincipal CustomUserDetail me,
            @RequestBody PhotoCompleteRequestDTO req
    ) {
        Long userId = me.getUser().getId();
        PhotoCompleteResponseDTO payload = photoService.complete(userId, req.locationId(), req.key());
        return ResponseEntity.ok(ApiResponse.success("photo", payload));
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<PhotoListItemDTO>>> myPhotos(
            @AuthenticationPrincipal CustomUserDetail me
    ) {
        Long userId = me.getUser().getId();
        List<PhotoListItemDTO> list = photoService.listMyPhotos(userId);
        return ResponseEntity.ok(ApiResponse.success("photos", list));
    }
}