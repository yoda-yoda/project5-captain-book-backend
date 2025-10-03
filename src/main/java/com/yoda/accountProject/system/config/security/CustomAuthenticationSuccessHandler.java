package com.yoda.accountProject.system.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoda.accountProject.system.common.response.ResponseData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        HttpStatus status = HttpStatus.OK;
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        // 성공시 응답 body에 넣을 공통 포맷 DTO
        ResponseData<String> resBody = ResponseData.<String>builder()
                .statusCode(status.value())
                .data("로그인에 성공하였습니다.")
                .build();

        objectMapper.writeValue(response.getOutputStream(), resBody);
    }
}
