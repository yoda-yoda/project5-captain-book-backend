package com.yoda.accountProject.system.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoda.accountProject.system.common.response.ResponseData;
import com.yoda.accountProject.system.config.security.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        String token = jwtTokenProvider.generateToken(authentication);

        HttpStatus status = HttpStatus.OK;
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");


        Map<String, String> accessTokenData = Map.of(
                "accessToken", token,
                "message", "로그인에 성공하였습니다."
        );

        // 성공시 응답 body에 넣을 공통 포맷 DTO
        ResponseData<Map<String, String>> resBody = ResponseData.<Map<String, String>>builder()
                .statusCode(status.value())
                .data(accessTokenData)
                .build();

        objectMapper.writeValue(response.getOutputStream(), resBody);
    }
}
