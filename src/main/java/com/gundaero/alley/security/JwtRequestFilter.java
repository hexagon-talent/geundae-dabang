package com.gundaero.alley.security;

import com.gundaero.alley.common.AuthErrorStatus;
import com.gundaero.alley.exception.AuthErrorException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtProvider;

    private static final String BEARER_PREFIX = "Bearer ";
    private static final AntPathMatcher PATH = new AntPathMatcher();

    private static final String[] WHITELIST = {
            "/api/public/**",
            "/api/auth/kakao",
            "/api/auth/refresh",
            "/api/auth/reissue",
            "/login/oauth2/**",
            "/oauth2/**",

            // swagger & actuator
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/actuator/**",

            // 기타
            "/favicon.ico",
            "/error"
    };

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        for (String pat : WHITELIST) {
            if (PATH.match(pat, uri)) return true;
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String uri = request.getRequestURI();
        log.debug("요청 URI: {}", uri);

        String jwt = resolveToken(request);
        if (StringUtils.hasText(jwt)) {
            log.debug("resolveToken - jwt tail=****{}", jwt.substring(Math.max(0, jwt.length() - 10)));
        }

        try {
            if (StringUtils.hasText(jwt)) {
                if (!jwtProvider.validateToken(jwt)) {
                } else {
                    Authentication authentication = jwtProvider.getAuthentication(jwt);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.debug("토큰 유효함. 인증 객체 설정 완료");
                }
            }

            filterChain.doFilter(request, response);

        } catch (UsernameNotFoundException e) {
            SecurityContextHolder.clearContext();
            throw new AuthErrorException(AuthErrorStatus.USER_NOT_FOUND);

        } catch (AuthErrorException e) {
            SecurityContextHolder.clearContext();
            log.warn("AuthErrorException: {}", e.getErrorStatus().getMsg());
            throw e;
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
            return token.substring(BEARER_PREFIX.length());
        }
        return null;
    }
}
