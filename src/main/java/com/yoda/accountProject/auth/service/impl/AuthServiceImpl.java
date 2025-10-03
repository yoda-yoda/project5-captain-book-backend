package com.yoda.accountProject.auth.service.impl;

import com.yoda.accountProject.auth.service.AuthService;
import com.yoda.accountProject.system.config.security.CustomOAuth2User;
import com.yoda.accountProject.system.config.security.CustomUserDetails;
import com.yoda.accountProject.system.exception.ExceptionMessage;
import com.yoda.accountProject.system.exception.auth.UserObjectTypeMismatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


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


}
