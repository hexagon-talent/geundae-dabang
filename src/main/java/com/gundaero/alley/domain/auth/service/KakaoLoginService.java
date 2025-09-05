package com.gundaero.alley.domain.auth.service;

import com.gundaero.alley.domain.auth.dto.request.KakaoProfileDTO;
import com.gundaero.alley.domain.auth.dto.response.KakaoLoginResponseDTO;
import com.gundaero.alley.domain.auth.entity.User;


public interface KakaoLoginService {
    KakaoProfileDTO getUserInfo(String accessToken);
    KakaoLoginResponseDTO createTokens(User user, String type);
    String delegateAccessToken(Long id, String email);
    KakaoLoginResponseDTO reissueTokens(String refreshToken);
    void unlink(Long kakaoId);
}
