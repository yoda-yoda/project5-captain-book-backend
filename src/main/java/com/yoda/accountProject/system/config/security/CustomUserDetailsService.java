package com.yoda.accountProject.system.config.security;

import com.yoda.accountProject.member.domain.Member;
import com.yoda.accountProject.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;


    // loadUserByUsername 메서드에서 예외가 발생하면 시큐리티는 모든 예외를 AuthenticationException 으로 래핑해서 던진다.
    // 이후 customAuthenticationFailureHandler 가 실패 로직을 담당한다.
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        String formProvider = "local";

        Member entity = memberRepository.findByProviderAndUserId(formProvider, userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저 정보를 찾을 수 없습니다."));

        return new CustomUserDetails(entity);
    }
}

