package com.yoda.accountProject.auth.service.impl;

import com.yoda.accountProject.auth.service.AuthService;
import com.yoda.accountProject.system.config.security.CustomOAuth2User;
import com.yoda.accountProject.system.exception.ExceptionMessage;
import com.yoda.accountProject.system.exception.auth.UserObjectTypeMismatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    public Long getOAuthCurrentMemberId (OAuth2User oauth2User) {

        // 만약 현재 사용자 정보가 담긴 객체인 oauth2User 객체가,
        // 직접 구현한 CustomOAuth2User 객체 타입과 호환되지 않는다면 오류를 던진다.
        if ( !(oauth2User instanceof CustomOAuth2User) ) {
            throw new UserObjectTypeMismatchException(ExceptionMessage.Auth.USER_OBJECT_TYPE_MISMATCH_ERROR);
        }

        return ((CustomOAuth2User) oauth2User).getMemberEntity().getId();
    }


}
