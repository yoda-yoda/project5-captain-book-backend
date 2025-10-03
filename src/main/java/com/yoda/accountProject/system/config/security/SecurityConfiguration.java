package com.yoda.accountProject.system.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;


@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, ClientRegistrationRepository repo) throws Exception {
        http

            .csrf(csrf -> {
                // 호환성 문제로 최신 XOR 핸들러를 UUID 토큰 기반 레거시 검증 핸들러로 교체
                // 핸들러 객체를 생성
                CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
                requestHandler.setCsrfRequestAttributeName(null);// XOR 검사 비활성화

                csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(requestHandler);
            })





                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/me", "/api/members/exits", "/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/members").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/formLogin").permitAll()
                        .anyRequest().authenticated()
                )




                // authenticationEntryPoint 를 이용해서 인증 정보가 없을경우 시큐리티의 기본 동작을 커스터마이징한다.
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customAuthenticationEntryPoint))



                .formLogin(form -> form
                        .loginProcessingUrl("/api/formLogin")   // 로그인 요청 받을 URL
                        .usernameParameter("userId")
                        .passwordParameter("password")
                        .successHandler(customAuthenticationSuccessHandler)
                        .failureHandler(customAuthenticationFailureHandler)
                )




                .oauth2Login(oauth2 -> {
                    oauth2
                            .defaultSuccessUrl("http://localhost:3000", true)


                            // OAuth 인증 에서 사용자를 리다이렉트하는 시점을 커스터마이징 한다.
                            // 그 중 authorizationRequestResolver 부분의 파라미터(prompt)를 커스터마이징 하여 재로그인시에도 계정 선택 화면이 나오도록 커스터마이징 한다.
                            // (원래는 재로그인시 계정 선택 화면도 안뜨고 바로 로그인됨)
                            .authorizationEndpoint(authorization ->
                                    authorization.authorizationRequestResolver(
                                            new CustomAuthorizationRequestResolver(repo)
                                    )
                            )

                            .failureHandler(customAuthenticationFailureHandler)

                            // OAuth 인증 후 사용자 프로필 정보를 가져오는 부분을 커스터 마이징한다.
                            .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService));

                })



                .logout(logout -> logout
                        .logoutUrl("/api/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessHandler(customLogoutSuccessHandler)
                );




        return http.build();
    }










}
