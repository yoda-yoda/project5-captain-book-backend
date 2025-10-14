package com.yoda.accountProject.system.config.security.jwt;

import com.yoda.accountProject.system.config.security.CustomUserDetailsService;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            if ( jwtTokenProvider.validateToken(token) ) {
                String subject = jwtTokenProvider.getSubject(token); // String의 memberId

                try {
                    Long memberId = Long.parseLong(subject);

                    UserDetails userDetails = customUserDetailsService.loadUserByMemberId(memberId);

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authentication);

                } catch (NumberFormatException ex) {
                    // 만약 subject가 숫자가 아닌 경우 무시하거나 로깅하기
                }
            }


            // 만약 jwt 헤더가 없거나 validateToken()이 false를 반환하는 등의 경우는 jwt 토큰이 유효하지않은 것이고
            // 따라서 SecurityContextHolder에 새로운 Authentication 객체가 설정되지 않은 상태로 다음 필터로 넘어간다.
        }

        filterChain.doFilter(request, response);


    }
}
