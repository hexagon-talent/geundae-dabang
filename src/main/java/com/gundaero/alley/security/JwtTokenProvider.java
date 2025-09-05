package com.gundaero.alley.security;

import com.gundaero.alley.common.AuthErrorStatus;
import com.gundaero.alley.domain.auth.dao.UserDAO;
import com.gundaero.alley.domain.auth.entity.CustomUserDetail;
import com.gundaero.alley.domain.auth.entity.RefreshToken;
import com.gundaero.alley.domain.auth.entity.User;
import com.gundaero.alley.domain.auth.service.CustomUserDetailService;
import com.gundaero.alley.domain.auth.service.RefreshTokenService;
import com.gundaero.alley.exception.AuthErrorException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.access.token.expiration.seconds:${jwt.access-ttl:900}}")
    private Long accessTokenValidationTime;

    @Value("${jwt.token.secret.key:${jwt.secret:dev-secret}}")
    private String secretKeyString;

    private Key secretKey;

    private final RefreshTokenService refreshTokenService;
    private final UserDAO userDAO;
    private final CustomUserDetailService userDetailService;

    @PostConstruct
    public void initializeSecretKey() {
        log.info("Loaded JWT Secret Key(raw): {}", secretKeyString);

        byte[] keyBytes = decodeSecret(secretKeyString);

        if (keyBytes.length < 32) {
            try {
                log.warn("JWT secret shorter than 32 bytes ({}). Deriving 32-byte key via SHA-256.", keyBytes.length);
                keyBytes = MessageDigest.getInstance("SHA-256").digest(keyBytes);
            } catch (Exception e) {
                throw new IllegalStateException("Failed to derive HS256 key from secret", e);
            }
        }

        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        log.info("JWT Secret initialized. (len={} bytes)", keyBytes.length);
    }

    private static byte[] decodeSecret(String raw) {
        String s = raw == null ? "" : raw.trim();
        try {
            if (s.startsWith("base64url:")) {
                return Decoders.BASE64URL.decode(s.substring("base64url:".length()));
            }
            if (s.startsWith("base64:")) {
                return Decoders.BASE64.decode(s.substring("base64:".length()));
            }
            return Decoders.BASE64URL.decode(s);
        } catch (IllegalArgumentException ignore) {
            try {
                return Decoders.BASE64.decode(s);
            } catch (IllegalArgumentException ignore2) {
                return s.getBytes(StandardCharsets.UTF_8);
            }
        }
    }

    public String generateAccessToken(Long id, String subject) {
        Instant now = Instant.now();
        return Jwts.builder()
                .claim("id", id)
                .setSubject(subject)
                .setExpiration(Date.from(now.plusSeconds(accessTokenValidationTime)))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(Long id) {
        RefreshToken refreshToken = new RefreshToken(UUID.randomUUID().toString(), id);
        refreshTokenService.save(refreshToken);
        return refreshToken.getRefreshToken();
    }

    public String reAccessToken(String token) throws AuthErrorException {
        RefreshToken refreshToken = refreshTokenService.findByToken(token)
                .orElseThrow(() -> new AuthErrorException(AuthErrorStatus.INVALID_TOKEN));

        Long userId = refreshToken.getUserId();
        User user = userDAO.findById(userId);

        return generateAccessToken(user.getId(), user.getEmail());
    }

    public boolean validateToken(String token) throws AuthErrorException {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.warn("잘못된 토큰: {}", e.getMessage());
            throw new AuthErrorException(AuthErrorStatus.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            log.warn("토큰 만료: {}", e.getMessage());
            throw new AuthErrorException(AuthErrorStatus.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.error("잘못된 JWT 토큰입니다.");
        }
        return false;
    }

    public Authentication getAuthentication(String accessToken) throws ExpiredJwtException {
        Claims claims = getTokenBody(accessToken);
        CustomUserDetail userDetail = userDetailService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetail, "", userDetail.getAuthorities());
    }

    public Claims getTokenBody(String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }
}
