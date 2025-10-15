package com.captainbook.auth.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;


// 현재 임시 비밀키를 통해 Jwt 액세스 토큰, 리프레시 토큰을 생성, 검증하며
// 토큰에서 사용자 유일 식별자인 MemberId의 String버전을 추출하여 사용자 정보를 얻도록 도와주는 로직이다

@Getter
@Component
public class JwtTokenProvider {

    // 임시 비밀 키 문자열이다. 현재는 개발 환경이라 편의상 사용하지만,
    // 실제 운영 환경에서는 안전을 위해 절대 하드코딩하지않고 환경 변수 등에서 로드해야 한다.
    // Base64 인코딩된 안전한 비밀 키 문자열이 필요하다 (HS256 사용 시 최소 32바이트 이상으로 하기)
    private static final String SECRET_KEY_BASE64 =

            // 현재는 개발용 임의의 키 이다
            "c3ByaW5nLWJvb3Qtand0LXR1dG9yaWFsLXNlY3JldC1rZXktZm9yLXRlc3QtcHVycG9zZXM=";

    // 액세스 토큰의 유효기간을 15분으로 설정한다
    private final long accessTokenValidity = 1000L * 60 * 15;

    // 리프레시 토큰의 유효기간을 3일로 설정한다
    private final long refreshTokenValidity = 1000L * 60 * 60 * 24 * 3;

    private final Key key;

    // Jwt 토큰 서명에 사용할 비밀 키를 생성한다
    public JwtTokenProvider() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY_BASE64);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }


    // 액세스 토큰 생성
    public String generateAccessToken(Authentication authentication) {

        // 현재 폼회원과 오아스회원을 모두 구현한 상태여서 두 종류의 유저 객체가 들어있다
        // 만약 Principal이 CustomOAuth2User 타입이면 authentication.getName()에서
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



    // 리프레시 토큰 생성
    public String generateRefreshToken(Authentication authentication) {

        // 현재 폼회원과 오아스회원을 모두 구현한 상태여서 두 종류의 유저 객체가 들어있다
        // 만약 Principal이 CustomOAuth2User 타입이면 authentication.getName()에서
        // 내부적으로 getName() 커스텀 메서드가 호출되고,
        // CustomUserDetails 타입이면 내부적으로 getUsername() 커스텀 메서드가 호출된다.
        String memberId = authentication.getName();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshTokenValidity);

        return Jwts.builder()
                .setSubject(memberId)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }




    // 토큰을 검증
    public boolean validateToken(String token) {
        try {

            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (Exception e) {

            // 이곳에서 토큰 만료, 잘못된 서명 등에 대한 다양한 예외 처리가 가능하다
            // 현재는 유효성 검증만 진행한다

            return false;
        }
    }


    // 토큰에서 사용자 식별자를 추출한다. 현재는 String 타입의 memberId(멤버 엔티티의 id)가 추출되도록 구현하였다
    public String getSubject(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }




}