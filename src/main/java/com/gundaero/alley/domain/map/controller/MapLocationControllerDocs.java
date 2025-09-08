package com.gundaero.alley.domain.map.controller;

import com.gundaero.alley.common.ApiResponse;
import com.gundaero.alley.domain.map.dto.response.MapLocationResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

public interface MapLocationControllerDocs {

    @Operation(
            summary = "지도 위치 목록",
            description =
                    """
                    투어 위치의 센터 포인트와 폴리곤을 (lat,lng) 배열로 반환합니다.
        
                    정렬: locationId(= DB id) ASC
        
                    2️⃣ 응답 구조:
                    ```json
                    {
                      "code": "0",
                      "message": "정상 처리 되었습니다.",
                      "data": [
                        {
                          "locationId": 1,
                          "title": "동산청라언덕",
                          "center": { "lat": 35.8666257294, "lng": 128.5849831341 },
                          "polygon": [
                            { "lat": 35.8667257294, "lng": 128.5848631341 },
                            { "lat": 35.8667257294, "lng": 128.5851031341 },
                            { "lat": 35.8665257294, "lng": 128.5851031341 },
                            { "lat": 35.8665257294, "lng": 128.5848631341 },
                            { "lat": 35.8667257294, "lng": 128.5848631341 }
                          ]
                        }
                      ]
                    }
                    ```
                    """
    )
    public ResponseEntity<ApiResponse<Object>> getAll();

    @Operation(
            summary = "지도 위치 단건 조회",
            description =
                    """
                    ️locationId로 단건을 조회합니다.
        
                    좌표: 응답은 (lat,lng) 배열
                    미존재 시: 404 반환
        
                    2️⃣ 응답 구조 (성공):
                    ```json
                    {
                      "code": "0",
                      "message": "정상 처리 되었습니다.",
                      "data": {
                        "locationId": 1,
                        "title": "동산청라언덕",
                        "center": { "lat": 35.8666257294, "lng": 128.5849831341 },
                        "polygon": [
                          { "lat": 35.8667257294, "lng": 128.5848631341 },
                          { "lat": 35.8667257294, "lng": 128.5851031341 },
                          { "lat": 35.8665257294, "lng": 128.5851031341 },
                          { "lat": 35.8665257294, "lng": 128.5848631341 },
                          { "lat": 35.8667257294, "lng": 128.5848631341 }
                        ]
                      }
                    }
                    ```
        
                    응답 구조 (404 Not Found):
                    ```json
                    {
                      "code": "404",
                      "message": "Location not found",
                      "data": null
                    }
                    ```
                    """
    )
    public ResponseEntity<ApiResponse<Object>> getOne(@PathVariable Long locationId);
}
