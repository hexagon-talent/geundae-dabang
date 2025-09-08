package com.gundaero.alley.domain.map.controller;

import com.gundaero.alley.common.ApiResponse;
import com.gundaero.alley.domain.auth.entity.CustomUserDetail;
import com.gundaero.alley.domain.map.service.MapLocationQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/map/locations")
@RequiredArgsConstructor
@Tag(name = "Polygon", description = "지도 관련 API")
public class MapLocationController implements MapLocationControllerDocs {

    private final MapLocationQueryService service;

    @GetMapping
    @Override
    public ResponseEntity<ApiResponse<Object>> getAll() {
        var list = service.getAll();
        return ResponseEntity.ok(ApiResponse.success("data", list));
    }

    @GetMapping("/{locationId}")
    @Override
    public ResponseEntity<ApiResponse<Object>> getOne(@PathVariable Long locationId) {
        return service.getOne(locationId)
                .<ResponseEntity<ApiResponse<Object>>>map(it ->
                        ResponseEntity.ok(ApiResponse.success("data", it))
                )
                .orElseGet(() ->
                        ResponseEntity.status(404).body(
                                ApiResponse.fail("404", "Location not found",
                                        java.util.Map.of("locationId", locationId))
                        )
                );
    }

    @GetMapping("/uploaded-status")
    @Override
    public ResponseEntity<ApiResponse<Object>> getMyUploadStatus(
            @AuthenticationPrincipal CustomUserDetail user
    ) {
        var list = service.getUploadStatusByUser(user.getUser().getId());
        return ResponseEntity.ok(ApiResponse.success("data", list));
    }

}
