package com.gundaero.alley.controller;

import com.gundaero.alley.common.ApiResponse;
import com.gundaero.alley.domain.auth.dto.request.KakaoLoginRequestDTO;
import com.gundaero.alley.domain.auth.dto.request.KakaoProfileDTO;
import com.gundaero.alley.domain.auth.dto.response.KakaoLoginResponseDTO;
import com.gundaero.alley.domain.auth.entity.User;
import com.gundaero.alley.domain.auth.service.KakaoLoginService;
import com.gundaero.alley.domain.auth.service.RefreshTokenService;
import com.gundaero.alley.domain.auth.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * DEV 전용 간단 헬스 체크 API
 * - GET /api/public/ping : 서버 살아있음 확인
 */
@RestController
@RequiredArgsConstructor
@Profile("dev")
public class PublicController {
    private final UserService userService;
    private final KakaoLoginService kakaoLoginService;
    private final RefreshTokenService refreshTokenService;

    @GetMapping("/api/public/ping")
    public Map<String, Object> ping() {
        return Map.of("ok", true);
    }

    @PostMapping("/kakao")
    public ResponseEntity<ApiResponse<String>> kakaoLogin(
            @RequestBody KakaoLoginRequestDTO requestDTO, HttpServletResponse response) {

        String kakaoAccessToken = requestDTO.getAccessToken();

        KakaoProfileDTO profile = kakaoLoginService.getUserInfo(kakaoAccessToken);

        User user = userService.upsertUser(profile);

        String loginType = user.getCreatedAt().equals(user.getUpdatedAt()) ? "signUp" : "Login";
        KakaoLoginResponseDTO tokenDTO = kakaoLoginService.createTokens(user, loginType);

        return ResponseEntity.ok(ApiResponse.success("tokenDTO", tokenDTO));
    }

}
