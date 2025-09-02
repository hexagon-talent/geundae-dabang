package com.gundaero.alley.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * DEV 전용 간단 헬스 체크 API
 * - GET /api/public/ping : 서버 살아있음 확인
 */
@RestController
@Profile("dev")
public class PublicController {

    @GetMapping("/api/public/ping")
    public Map<String, Object> ping() {
        return Map.of("ok", true);
    }
}
