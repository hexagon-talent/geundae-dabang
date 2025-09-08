package com.gundaero.alley.domain.map.controller;

import com.gundaero.alley.domain.map.dto.response.MapLocationResponseDTO;
import com.gundaero.alley.domain.map.service.MapLocationQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/map/locations")
@RequiredArgsConstructor
@Tag(name = "Polygon", description = "지도 관련 API")
public class MapLocationController implements MapLocationControllerDocs {

    private final MapLocationQueryService service;

    @GetMapping
    @Override
    public ResponseEntity<Map<String, List<MapLocationResponseDTO>>> getAll() {
        var list = service.getAll();
        return ResponseEntity.ok(Map.of("data", list));
    }

    @GetMapping("/{locationId}")
    @Override
    public ResponseEntity<Map<String, Object>> getOne(@PathVariable Long locationId) {
        return service.getOne(locationId)
                .<ResponseEntity<Map<String, Object>>>map(it -> ResponseEntity.ok(Map.of("data", it)))
                .orElseGet(() -> ResponseEntity.status(404).body(
                        Map.of("message", "Location not found", "locationId", locationId)
                ));
    }
}

