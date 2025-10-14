package com.yoda.accountProject.system.config.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // 🚨 주의: 실제 운영 환경에서는 절대 코드에 하드코딩하지 않고, 환경 변수 등에서 로드해야 합니다.
    // Base64 인코딩된 안전한 비밀 키 문자열이 필요합니다. (HS256 사용 시 최소 32바이트 이상 권장)
    private static final String SECRET_KEY_BASE64 =
            "c3ByaW5nLWJvb3Qtand0LXR1dG9yaWFsLXNlY3JldC1rZXktZm9yLXRlc3QtcHVycG9zZXM="; // 예시 키

    private final long accessTokenValidity = 1000L * 60 * 30; // 30분

    private final Key key;

    // Key 초기화: @RequiredArgsConstructor로 생성된 생성자에서 이 Key를 주입하거나,
    // 아래와 같이 초기화 블록에서 직접 생성합니다. (일반적으로 Bean 초기화 시 수행)
    public JwtTokenProvider() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY_BASE64);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }


    // 토큰 생성
    public String generateToken(Authentication authentication) {

        // Principal이 CustomOAuth2User 타입이면 authentication.getName()에서
        // 내부적으로 getName() 커스텀 메서드가 호출되고,
        // CustomUserDetails 타입이면 내부적으로 getUsername() 커스텀 메서드가 호출된다.
        String memberId = authentication.getName();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenValidity);

        return Jwts.builder()
                .setSubject(memberId)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


    // 토큰 검증
    public boolean validateToken(String token) {
        try {

            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (Exception e) {

            // 토큰 만료, 잘못된 서명 등 다양한 예외 처리
            return false;
        }
    }


    // 사용자 식별자 추출. 현재는 String 타입의 memberId로 구현함
    public String getSubject(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }



}