package com.gundaero.alley.domain.auth.dao;

import com.gundaero.alley.domain.auth.dto.request.KakaoProfileDTO;
import com.gundaero.alley.domain.auth.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDAO {
    int upsertUser(KakaoProfileDTO profile);
    int insertUser(KakaoProfileDTO user);
    boolean existingUser(Long kakaoId);
    User findById(Long id);
    User findByEmail(String email);
    User findByKakaoId(Long kakaoId);
    int deleteById(Long id);
}
