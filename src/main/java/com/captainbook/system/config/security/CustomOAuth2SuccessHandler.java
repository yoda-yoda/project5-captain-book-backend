package com.captainbook.system.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.captainbook.auth.domain.RefreshToken;
import com.captainbook.auth.repository.RefreshTokenRepository;
import com.captainbook.auth.security.JwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;
    private final RefreshTokenRepository refreshTokenRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {


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


        String redirectDomain = "https://localhost:3000/oauth/point";

        // 5. 현재 OAuth 로그인은 accessToken을 (body에 담는) 폼로그인과 달리,
        // 오아스 로그인 팝업창에 쿼리파라미터 방식으로 보내준다. 리다이렉트되자마자 팝업창은 바로 닫히도록 되어있다.
        String redirectUrl = UriComponentsBuilder
                .fromUriString(redirectDomain)
                .queryParam("accessToken", accessToken)
                .build()
                .toUriString();

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);


    }
}
