package com.gundaero.alley.domain.tour.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gundaero.alley.domain.tour.dto.request.TestRequestDTO;
import com.gundaero.alley.domain.tour.dto.response.LocationBasedFoodResponseDTO;
import com.gundaero.alley.util.TextSanitizer;
import com.gundaero.alley.domain.tour.dao.TourDAO;
import com.gundaero.alley.domain.tour.dto.response.TourOverviewResponseDTO;
import com.gundaero.alley.domain.tour.entity.TourLocation;
import com.gundaero.alley.external.TourApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TourService {

    private final TourDAO tourDAO;
    private final TourApiClient tourApiClient;
    private final ObjectMapper om = new ObjectMapper();

    @Value("${tourapi.app-name}")   private String appName;
    @Value("${tourapi.service-key}") private String serviceKey;

    public TourOverviewResponseDTO getOverview(Long locationId) {
        TourLocation loc = tourDAO.findById(locationId);
        if (loc == null) throw new IllegalArgumentException("invalid locationId");

        Map<String, Object> res = tourApiClient.getDetailCommon(
                "ETC", appName, "json", String.valueOf(loc.getContentId()), serviceKey
        );


        JsonNode root  = om.convertValue(res, JsonNode.class);
        JsonNode itemN = root.path("response").path("body").path("items").path("item");
        if (itemN.isMissingNode() || itemN.isNull()) return null;

        JsonNode first = itemN.isArray() ? itemN.get(0) : itemN;

        String titleApi    = first.path("title").asText(null);
        String overviewRaw = first.path("overview").asText(null);
        if (overviewRaw == null || overviewRaw.isBlank()) return null;

        String title = (titleApi != null && !titleApi.isBlank()) ? titleApi : loc.getTitle();

        return TourOverviewResponseDTO.builder()
                .title(TextSanitizer.clean(title))
                .overview(TextSanitizer.clean(overviewRaw))
                .build();
    }
    public List<LocationBasedFoodResponseDTO> getNearbyFoods(double mapX, double mapY, Integer radius, Integer pageNo, Integer numOfRows) {
        radius = Math.min(radius, 20000);
        Map<String, Object> res = tourApiClient.getLocationBasedFood(
                serviceKey,
                "ETC",
                appName,
                "json",
                mapX, mapY, radius,
                "S",
                "39",
                pageNo, numOfRows
        );

        JsonNode root  = om.convertValue(res, JsonNode.class);
        JsonNode items = root.path("response").path("body").path("items").path("item");
        if (items.isMissingNode() || items.isNull()) return List.of();

        List<LocationBasedFoodResponseDTO> list = new ArrayList<>();
        if (items.isArray()) {
            for (JsonNode n : items) list.add(toFoodDTO(n));
        } else {
            list.add(toFoodDTO(items));
        }

        list.sort(
                java.util.Comparator.comparing(
                        LocationBasedFoodResponseDTO::getDist,
                        java.util.Comparator.nullsLast(Double::compareTo)
                )
        );



        return list;
    }

    private static Double toDouble(JsonNode n) {
        if (n == null || n.isNull()) return null;
        String s = n.asText(null);
        if (s == null || s.isBlank()) return null;
        try { return Double.valueOf(s); }
        catch (NumberFormatException e) { return null; }
    }

    private LocationBasedFoodResponseDTO toFoodDTO(JsonNode n) {
        return new LocationBasedFoodResponseDTO(
                n.path("title").asText(null),
                n.path("addr1").asText(null),
                n.path("addr2").asText(null),
                n.path("tel").asText(null),
                toDouble(n.path("mapx")),
                toDouble(n.path("mapy")),
                toDouble(n.path("dist"))
        );
    }

    public List<LocationBasedFoodResponseDTO> getNearbyEvents(
            double mapX, double mapY, int radius, int pageNo, int numOfRows) {

        radius = Math.min(radius, 20000);

        Map<String, Object> res = tourApiClient.getLocationBasedFood(
                serviceKey,
                "ETC",
                appName,
                "json",
                mapX, mapY, radius,
                "S",
                "15",
                pageNo, numOfRows
        );

        JsonNode root  = om.convertValue(res, JsonNode.class);
        JsonNode items = root.path("response").path("body").path("items").path("item");
        if (items.isMissingNode() || items.isNull()) return List.of();

        List<LocationBasedFoodResponseDTO> list = new ArrayList<>();
        if (items.isArray()) {
            for (JsonNode n : items) list.add(toFoodDTO(n));
        } else {
            list.add(toFoodDTO(items));
        }

        list.sort(
                java.util.Comparator.comparing(
                        LocationBasedFoodResponseDTO::getDist,
                        java.util.Comparator.nullsLast(Double::compareTo)
                )
        );



        return list;
    }

    @Transactional
    public void saveUserTourLocations(TestRequestDTO request) {
        tourDAO.insertOrUpdateUserTourLocations(request);
    }

}
