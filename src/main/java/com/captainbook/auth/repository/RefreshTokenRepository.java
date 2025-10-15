package com.captainbook.auth.repository;

import com.captainbook.auth.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

// 리프레시 토큰을 저장하는 Redis의 저장소다
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {

}

