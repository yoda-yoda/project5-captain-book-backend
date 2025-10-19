package com.captainbook.auth.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;


// Redis에 저장할 리프레시 토큰의 엔티티이다

// Redis의 Key Prefix이다
@RedisHash("refreshToken")
@Getter
public class RefreshToken {

    @Id
    // 리프레시 토큰 값을 식별하는 Redis Key임과 동시에 유일한 사용자 식별자이다
    private Long memberId;

    // 리프레시 토큰이 여기 저장된다
    private String refreshToken;

    // 토큰 값의 Time-To-Live (만료 시간)을 설정한다
    @TimeToLive
    private Long expiration;

    @Builder
    public RefreshToken(Long memberId, String refreshToken, Long expiration) {
        this.memberId = memberId;
        this.refreshToken = refreshToken;
        this.expiration = expiration;
    }

    public void updateToken(String newRefreshTokenToken, Long newExpiration) {
        this.refreshToken = newRefreshTokenToken;
        this.expiration = newExpiration;
    }
}