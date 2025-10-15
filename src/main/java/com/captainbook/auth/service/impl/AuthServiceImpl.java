package com.captainbook.auth.service.impl;

import com.captainbook.auth.repository.RefreshTokenRepository;
import com.captainbook.auth.service.AuthService;
import com.captainbook.system.config.security.CustomOAuth2User;
import com.captainbook.system.config.security.CustomUserDetails;
import com.captainbook.system.exception.ExceptionMessage;
import com.captainbook.system.exception.auth.UserObjectTypeMismatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


// 인증 관련 로직의 서비스 계층이다

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    // Redis에 토큰 저장 시 설정한 키의 접두사를 미리 선언한다. (예: "refreshToken")
    private final String REDIS_KEY_PREFIX = "refreshToken:";
    private final RefreshTokenRepository refreshTokenRepository;


    // 목적: 현재 멤버id를 반환한다
    public Long getCurrentMemberId (Object principal) {


        // 만약 현재 사용자 정보가 담긴 객체인 principal 객체가,
        // 직접 구현한 CustomOAuth2User나 CustomUserDetails 객체 타입과 호환되지 않는다면 오류를 던진다.
        if (principal instanceof CustomOAuth2User customOAuth2User) {
            return customOAuth2User.getMemberEntity().getId();
        } else if (principal instanceof CustomUserDetails customUserDetails) {
            return customUserDetails.getMemberEntity().getId();
        } else {
            throw new UserObjectTypeMismatchException(ExceptionMessage.Auth.USER_OBJECT_TYPE_MISMATCH_ERROR);
        }

    }

    // 목적: 멤버 id로(키로) Redis에서 리프레시 토큰을 삭제한다
    public void deleteRefreshTokenByMemberId(String memberId) {

        Long longMemberId = Long.parseLong(memberId);
        refreshTokenRepository.deleteById(longMemberId);
    }


}
