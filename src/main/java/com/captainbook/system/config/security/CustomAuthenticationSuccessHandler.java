package com.captainbook.system.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.captainbook.auth.domain.RefreshToken;
import com.captainbook.auth.repository.RefreshTokenRepository;
import com.captainbook.system.common.response.ResponseData;
import com.captainbook.auth.security.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
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
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        // 1. Access Token 과 Refresh Token 를 발급한다
        String accessToken = jwtTokenProvider.generateAccessToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);

        // 2. memberId를 (멤버 엔티티 id를) 추출한다
        Long memberId = Long.parseLong(authentication.getName());

        // Redis TTL은 초 단위로 설정되므로 밀리초를 초로 변환해준다
        long expirySeconds = jwtTokenProvider.getRefreshTokenValidity() / 1000;

        // 3. 초를 설정해 리프레시 토큰을 Redis에 저장한다
        RefreshToken rt = RefreshToken.builder()
                .memberId(memberId)
                .refreshToken(refreshToken)
                .expiration(expirySeconds)
                .build();

        refreshTokenRepository.save(rt);



        // 4. HttpOnly Cookie에 리프레시 토큰을 담는다
        // 보안을 위해 JS가 쿠키에 접근하지못하도록하고, HTTPS를 사용하도록하고, 초 단위로 설정하며 모든 경로에서 쿠키를 전송하는 과정이다.
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setMaxAge((int) expirySeconds);
        refreshTokenCookie.setPath("/");
        response.addCookie(refreshTokenCookie);


        // 5. Access Token 은 응답 본문에 담아 클라이언트에 전송한다
        HttpStatus status = HttpStatus.OK;
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");


        Map<String, String> jwtTokenData = Map.of(
                "accessToken", accessToken,
                "message", "로그인에 성공하였습니다."
        );

        // 성공시 응답 body에 넣을 공통 포맷 DTO 를 생성한다
        ResponseData<Map<String, String>> resBody = ResponseData.<Map<String, String>>builder()
                .statusCode(status.value())
                .data(jwtTokenData)
                .build();

        objectMapper.writeValue(response.getOutputStream(), resBody);

    }
}
