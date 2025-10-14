package com.yoda.accountProject.system.config.security;

import com.yoda.accountProject.member.repository.MemberRepository;
import com.yoda.accountProject.system.config.security.jwt.JwtTokenProvider;
import jakarta.servlet.ServletException;
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
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        log.info("OAuth2 로그인 성공 => JWT 발급 시작");

        // JWT 생성
        String accessToken = jwtTokenProvider.generateToken(authentication);

        // 리액트로 리다이렉트 (JWT 포함)
        String redirectUrl = UriComponentsBuilder
                .fromUriString("http://localhost:3000/oauth/point")
                .queryParam("accessToken", accessToken)
                .build()
                .toUriString();

        log.info("JWT 발급 완료 => redirect: {}", redirectUrl);

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
