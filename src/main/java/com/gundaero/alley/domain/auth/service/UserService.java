package com.gundaero.alley.domain.auth.service;

import com.gundaero.alley.domain.auth.dto.request.KakaoProfileDTO;
import com.gundaero.alley.domain.auth.dto.response.UserSimpleInfoDTO;
import com.gundaero.alley.domain.auth.entity.User;

public interface UserService {

    User insertUser(KakaoProfileDTO user);
    User upsertUser(KakaoProfileDTO kakaoProfile);
    boolean existingUser(Long kakaoId);
    void withdrawHard(Long userId, Long kakaoId);

    User findById(Long id);
    User findByEmail(String email);
    User findByKakaoId(Long kakaoId);
    UserSimpleInfoDTO findByIdUserSimpleDTO(Long id);

}
