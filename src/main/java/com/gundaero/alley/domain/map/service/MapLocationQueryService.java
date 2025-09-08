package com.gundaero.alley.domain.map.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gundaero.alley.domain.map.dto.response.LatLng;
import com.gundaero.alley.domain.map.dto.response.MapLocationResponseDTO;
import com.gundaero.alley.domain.map.dto.response.MapLocationUploadStatusResponseDTO;
import com.gundaero.alley.domain.map.dao.MapLocationDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MapLocationQueryService {

    private final MapLocationDAO mapper;
    private final ObjectMapper objectMapper;

    private static LatLng toLatLng(double lon, double lat) {
        return new LatLng(lat, lon);
    }

    @SuppressWarnings("unchecked")
    public List<MapLocationResponseDTO> getAll() {
        var rows = mapper.findAllAsGeoJson();
        List<MapLocationResponseDTO> list = new ArrayList<>(rows.size());

        for (var r : rows) {
            Long locationId = ((Number) r.get("id")).longValue();
            String title = (String) r.get("title");
            
            Map<String, Object> center = readJson((String) r.get("center_geojson"));
            List<Number> ccoords = (List<Number>) center.get("coordinates");
            LatLng centerLatLng = toLatLng(
                    ccoords.get(0).doubleValue(), 
                    ccoords.get(1).doubleValue() 
            );
            
            Map<String, Object> poly = readJson((String) r.get("polygon_geojson"));
            List<List<List<Number>>> rings = (List<List<List<Number>>>) poly.get("coordinates");
            List<List<Number>> outer = rings.get(0);
            List<LatLng> polygon = new ArrayList<>(outer.size());
            for (List<Number> p : outer) {
                polygon.add(toLatLng(
                        p.get(0).doubleValue(), 
                        p.get(1).doubleValue()  
                ));
            }

            list.add(new MapLocationResponseDTO(locationId, title, centerLatLng, polygon));
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    public Optional<MapLocationResponseDTO> getOne(Long id) {
        var r = mapper.findOneAsGeoJson(id);
        if (r == null) return Optional.empty();

        Long locationId = ((Number) r.get("id")).longValue();
        String title = (String) r.get("title");

        Map<String, Object> center = readJson((String) r.get("center_geojson"));
        List<Number> ccoords = (List<Number>) center.get("coordinates");
        LatLng centerLatLng = toLatLng(
                ccoords.get(0).doubleValue(),
                ccoords.get(1).doubleValue()  
        );

        Map<String, Object> poly = readJson((String) r.get("polygon_geojson"));
        List<List<List<Number>>> rings = (List<List<List<Number>>>) poly.get("coordinates");
        List<List<Number>> outer = rings.get(0);
        List<LatLng> polygon = new ArrayList<>(outer.size());
        for (List<Number> p : outer) {
            polygon.add(toLatLng(
                    p.get(0).doubleValue(),
                    p.get(1).doubleValue()
            ));
        }

        return Optional.of(new MapLocationResponseDTO(locationId, title, centerLatLng, polygon));
    }

    private Map<String, Object> readJson(String s) {
        try {
            return objectMapper.readValue(s, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("유효하지 않은 GeoJSON: " + s, e);
        }
    }

    public List<MapLocationUploadStatusResponseDTO> getUploadStatusByUser(Long userId) {
        List<Map<String, Object>> rows = mapper.findAllUploadStatusByUser(userId);
        List<MapLocationUploadStatusResponseDTO> out = new ArrayList<>(rows.size());
        for (var r : rows) {
            Long   locationId = ((Number) r.get("location_id")).longValue();
            String title      = (String) r.get("title");
            boolean uploaded  = toBool(r.get("uploaded"));
            out.add(new MapLocationUploadStatusResponseDTO(locationId, title, uploaded));
        }
        return out;
    }

    private boolean toBool(Object v) {
        if (v instanceof Boolean b) return b;
        if (v instanceof Number n)  return n.intValue() != 0;
        if (v instanceof String s)  return "true".equalsIgnoreCase(s) || "1".equals(s);
        return false;
    }

}
