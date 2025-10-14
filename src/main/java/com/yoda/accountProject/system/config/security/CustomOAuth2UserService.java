package com.yoda.accountProject.system.config.security;

import com.yoda.accountProject.member.domain.Member;
import com.yoda.accountProject.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberService memberService;

    // loadUser 메서드에서 예외가 발생하면 시큐리티는 모든 예외를 OAuth2AuthenticationException 으로 래핑해서 던진다.
    // 이후 OAuth2LoginAuthenticationFilter가 AuthenticationFailureHandler를 호출하고 로그인 실패를 처리한다.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // "google", "kakao", "naver" 등의 provider 이다.
        String provider = userRequest.getClientRegistration().getRegistrationId();

        // OAuth 사용자 정보를 가져오기
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // DB에 회원 정보가 있으면 그대로 반환하고, 없으면 저장 후에 반환한다.
        Member memberEntity = memberService.saveOAuthMemberFromLoadUser(provider, oAuth2User.getAttributes());

        return new CustomOAuth2User(memberEntity, oAuth2User.getAttributes());

    }


}
