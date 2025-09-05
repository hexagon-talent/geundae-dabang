package com.gundaero.alley.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gundaero.alley.common.ApiResponse;
import com.gundaero.alley.exception.AuthErrorException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (AuthErrorException e) {
            handleAuthException(response, e);
        }
    }

    private void handleAuthException(HttpServletResponse response, AuthErrorException e) throws IOException, IOException {
        if (response.isCommitted()) return;

        int status = e.getCode().getCode();

        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ApiResponse<Object> responseBody = ApiResponse.fail(
                String.valueOf(status),
                e.getErrorMsg()
        );

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), responseBody);
    }
}
