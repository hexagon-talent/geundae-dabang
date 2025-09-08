package com.gundaero.alley.domain.map.controller;

import com.gundaero.alley.common.ApiResponse;
import com.gundaero.alley.domain.map.service.MapLocationQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/map/locations")
@RequiredArgsConstructor
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
}
