package com.gundaero.alley.domain.tour.controller;

import com.gundaero.alley.common.ApiResponse;
import com.gundaero.alley.domain.tour.dto.request.TestRequestDTO;
import com.gundaero.alley.domain.tour.dto.response.LocationBasedFoodResponseDTO;
import com.gundaero.alley.domain.tour.dto.response.TourOverviewResponseDTO;
import com.gundaero.alley.domain.tour.service.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tour")
@RequiredArgsConstructor
@Validated
public class TourController implements TourControllerDocs {

    private final TourService tourService;

    @Override
    @GetMapping("/overview")
    public ResponseEntity<ApiResponse<TourOverviewResponseDTO>> getOverview(
            @RequestParam Long locationId
    ) {
        TourOverviewResponseDTO dto = tourService.getOverview(locationId);
        if (dto == null) {
            return ResponseEntity.ok(ApiResponse.fail("NO_CONTENT", "overview not found"));
        }
        return ResponseEntity.ok(ApiResponse.success("tour", dto));
    }

    @Override
    @GetMapping("/location-based/food")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getLocationBasedFood(
            @RequestParam double mapX,
            @RequestParam double mapY,
            @RequestParam(defaultValue = "1000") int radius,
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "50") int numOfRows
    ) {

        radius = Math.min(radius, 20000);

        List<LocationBasedFoodResponseDTO> items =
                tourService.getNearbyFoods(mapX, mapY, radius, pageNo, numOfRows);

        Map<String, Object> payload = new HashMap<>();
        payload.put("mapX", mapX);
        payload.put("mapY", mapY);
        payload.put("radius", radius);
        payload.put("pageNo", pageNo);
        payload.put("numOfRows", numOfRows);
        payload.put("items", items);

        return ResponseEntity.ok(ApiResponse.success("foods", payload));
    }

    @Override
    @GetMapping("/location-based/event")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getLocationBasedEvents(
            @RequestParam double mapX,
            @RequestParam double mapY,
            @RequestParam(defaultValue = "1000") int radius,
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "50") int numOfRows
    ) {

        radius = Math.min(radius, 20000);

        List<LocationBasedFoodResponseDTO> items =
                tourService.getNearbyEvents(mapX, mapY, radius, pageNo, numOfRows);

        Map<String, Object> payload = new HashMap<>();
        payload.put("mapX", mapX);
        payload.put("mapY", mapY);
        payload.put("radius", radius);
        payload.put("pageNo", pageNo);
        payload.put("numOfRows", numOfRows);
        payload.put("items", items);


        return ResponseEntity.ok(ApiResponse.success("events", payload));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> saveUserTourLocations(
            @RequestBody TestRequestDTO request) {
        tourService.saveUserTourLocations(request);
        return ResponseEntity.ok(ApiResponse.success("result", null));
    }

}
