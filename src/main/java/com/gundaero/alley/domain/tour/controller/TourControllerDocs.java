package com.gundaero.alley.domain.tour.controller;

import com.gundaero.alley.common.ApiResponse;
import com.gundaero.alley.domain.tour.dto.response.TourOverviewResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Tag(name = "Tour", description = "근대골목 투어 API")
public interface TourControllerDocs {

    @Operation(
            summary = "개요(overview) 조회",
            description =
                    "전달받은 **locationId**로 DB에서 contentId를 찾고, TourAPI **detailCommon2**를 호출하여 `title`, `overview`를 반환합니다.\n\n" +
                            "1️⃣ **동작**\n" +
                            "- DB: `tour_location.id → content_id` 조회\n" +
                            "- 외부: `detailCommon2?MobileOS=ETC&MobileApp=...&_type=json&contentId=...` 호출\n" +
                            "- 응답: HTML/엔티티 정리된 `title`, `overview` 반환\n\n" +
                            "2️⃣ **요청 파라미터**\n" +
                            "- `locationId` (Long, 필수)\n\n" +
                            "3️⃣ **예시**\n" +
                            "GET /api/tour/overview?locationId=1\n\n" +
                            "4️⃣ **성공 응답 예시**\n" +
                            "```json\n" +
                            "{\n" +
                            "  \"code\": \"0\",\n" +
                            "  \"message\": \"정상 처리 되었습니다.\",\n" +
                            "  \"data\": {\n" +
                            "    \"tour\": {\n" +
                            "      \"title\": \"대구 계산동성당\",\n" +
                            "      \"overview\": \"계산성당은 중구 계산오거리에 위치하며 범어대성당과 함께 천주교 대구대교구의 공동 주교좌성당이다.\"\n" +
                            "    }\n" +
                            "  }\n" +
                            "}\n" +
                            "```"
    )
    ResponseEntity<ApiResponse<TourOverviewResponseDTO>> getOverview(
            @RequestParam Long locationId
    );

    @Operation(
            summary = "근처 음식점(거리순) 조회",
            description =
                    "GPS 좌표를 기준으로 반경 내 음식점(contentTypeId=39)을 거리순(arrange=S)으로 조회합니다.\n\n" +
                            "1️⃣ **동작**\n" +
                            "- 외부: `locationBasedList2?...&arrange=S&contentTypeId=39` 호출\n" +
                            "- 응답: `title, addr1, addr2, tel, mapx, mapy, dist` 반환\n\n" +
                            "2️⃣ **요청 파라미터**\n" +
                            "- `mapX` (double, 필수, 경도 [-180, 180])\n" +
                            "- `mapY` (double, 필수, 위도 [-90, 90])\n" +
                            "- `radius` (int, 선택, 기본=1000, 최대=20000)\n" +
                            "- `pageNo` (int, 선택, 기본=1)\n" +
                            "- `numOfRows` (int, 선택, 기본=50)\n\n" +
                            "3️⃣ **예시**\n" +
                            "GET /api/tour/location-based/food?mapX=127.0276&mapY=37.4979&radius=1500&pageNo=1&numOfRows=50\n\n" +
                            "4️⃣ **성공 응답 예시**\n" +
                            "```json\n" +
                            "{\n" +
                            "  \"code\": \"0\",\n" +
                            "  \"message\": \"정상 처리 되었습니다.\",\n" +
                            "  \"data\": {\n" +
                            "    \"foods\": {\n" +
                            "      \"mapX\": 128.5919046844,\n" +
                            "      \"mapY\": 35.8688783799,\n" +
                            "      \"radius\": 1000,\n" +
                            "      \"pageNo\": 1,\n" +
                            "      \"numOfRows\": 50,\n" +
                            "      \"items\": [\n" +
                            "        {\n" +
                            "          \"title\": \"홍길동식당\",\n" +
                            "          \"addr1\": \"대구 중구 어딘가 12\",\n" +
                            "          \"addr2\": \"지하 1층\",\n" +
                            "          \"tel\": \"053-123-4567\",\n" +
                            "          \"mapx\": 128.5923,\n" +
                            "          \"mapy\": 35.8687,\n" +
                            "          \"dist\": 145.0\n" +
                            "        }\n" +
                            "      ]\n" +
                            "    }\n" +
                            "  }\n" +
                            "}\n" +
                            "```"
    )
    ResponseEntity<ApiResponse<Map<String, Object>>> getLocationBasedFood(
            @RequestParam double mapX,
            @RequestParam double mapY,
            @RequestParam(defaultValue = "1000") int radius,
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "50") int numOfRows
    );

    @Operation(
            summary = "근처 행사(거리순) 조회",
            description =
                    "GPS 좌표를 기준으로 반경 내 **행사(축제/공연, contentTypeId=15)** 를 **거리순(arrange=S)** 으로 조회합니다.\\n\\n" +
                            "1️⃣ **동작**\n" +
                            "- 외부: `locationBasedList2?...&arrange=S&contentTypeId=15` 호출\n" +
                            "- 응답: `title, addr1, addr2, tel, mapx, mapy, dist` 반환\n\n" +
                            "2️⃣ **요청 파라미터**\n" +
                            "- `mapX` (double, 필수, 경도 [-180, 180])\n" +
                            "- `mapY` (double, 필수, 위도 [-90, 90])\n" +
                            "- `radius` (int, 선택, 기본=1000, 최대=20000)\n" +
                            "- `pageNo` (int, 선택, 기본=1)\n" +
                            "- `numOfRows` (int, 선택, 기본=50)\n\n" +
                            "3️⃣ **예시**\n" +
                            "GET /api/tour/location-based/event?mapX=127.0276&mapY=37.4979&radius=1500&pageNo=1&numOfRows=50\n\n" +
                            "4️⃣ **성공 응답 예시**\n" +
                            "```json\n" +
                            "{\n" +
                            "  \"code\": \"0\",\n" +
                            "  \"message\": \"정상 처리 되었습니다.\",\n" +
                            "  \"data\": {\n" +
                            "    \"foods\": {\n" +
                            "      \"mapX\": 128.5919046844,\n" +
                            "      \"mapY\": 35.8688783799,\n" +
                            "      \"radius\": 1000,\n" +
                            "      \"pageNo\": 1,\n" +
                            "      \"numOfRows\": 50,\n" +
                            "      \"items\": [\n" +
                            "        {\n" +
                            "          \"title\": \"대구 국가유산 야행\",\n" +
                            "          \"addr1\": \"대구 중구 어딘가 12\",\n" +
                            "          \"addr2\": \"지하 1층\",\n" +
                            "          \"tel\": \"053-123-4567\",\n" +
                            "          \"mapx\": 128.5923,\n" +
                            "          \"mapy\": 35.8687,\n" +
                            "          \"dist\": 145.0\n" +
                            "        }\n" +
                            "      ]\n" +
                            "    }\n" +
                            "  }\n" +
                            "}\n" +
                            "```"
    )
    public ResponseEntity<ApiResponse<Map<String, Object>>> getLocationBasedEvents(
            @RequestParam double mapX,
            @RequestParam double mapY,
            @RequestParam(defaultValue = "1000") int radius,
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "50") int numOfRows
    );
}
