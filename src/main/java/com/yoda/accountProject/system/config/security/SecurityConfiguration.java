package com.yoda.accountProject.system.config.security;

import com.yoda.accountProject.system.config.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, ClientRegistrationRepository repo) throws Exception {
        http


                // 이제는 JWT 방식으로 변경할것이고 세션 기반이 아니므로 CSRF 비활성화
                .csrf(AbstractHttpConfigurer::disable)



//            밑의 csrf 설정은 세션기반의 csrf 토큰을 사용했을때 설정값이다.
//            .csrf(csrf -> {
//                // 호환성 문제로 최신 XOR 핸들러를 UUID 토큰 기반 레거시 검증 핸들러로 교체
//                // 핸들러 객체를 생성
//                CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
//                requestHandler.setCsrfRequestAttributeName(null);// XOR 검사 비활성화
//
//                csrf
//                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                        .csrfTokenRequestHandler(requestHandler);
//            })





                .cors(Customizer.withDefaults())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 이제는 JWT 방식으로 변경할것이라 세션 사용 X
                )

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/error", "/error/**").permitAll()
                        .requestMatchers("/api/auth/me", "/api/members/exits", "/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/members").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/formLogin").permitAll()
                        .anyRequest().authenticated()
                )



                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // JWT 로직 필터를 인증 필터 앞에 추가




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

//                            .defaultSuccessUrl("http://localhost:3000", true) // jwt 방식이라서 커스텀을 만들었기때문에 주석.


                            // OAuth 인증 에서 사용자를 리다이렉트하는 시점을 커스터마이징 한다.
                            // 그 중 authorizationRequestResolver 부분의 파라미터(prompt)를 커스터마이징 하여 재로그인시에도 계정 선택 화면이 나오도록 커스터마이징 한다.
                            // (원래는 재로그인시 계정 선택 화면도 안뜨고 바로 로그인됨)
                            .authorizationEndpoint(authorization ->
                                    authorization.authorizationRequestResolver(
                                            new CustomAuthorizationRequestResolver(repo)
                                    )
                            )



                            // OAuth 인증 성공 후 사용자 프로필 정보를 가져오는 부분을 커스터 마이징한다.
                            .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))

                            .failureHandler(customAuthenticationFailureHandler)

                            .successHandler(customOAuth2SuccessHandler)
                            ;

                })


//          현재는 jwt라 로그아웃은 필요없음
//                .logout(logout -> logout
//                        .logoutUrl("/api/logout")
////                        .invalidateHttpSession(true) // jwt에서는 세션 기반이 아니므로 삭제
////                        .deleteCookies("JSESSIONID") // jwt에서는 JSESSIONID 기반이 아니므로 삭제
//                        .logoutSuccessHandler(customLogoutSuccessHandler)
//                )

                    ;



        return http.build();
    }










}
