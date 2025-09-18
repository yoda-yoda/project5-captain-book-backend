package com.yoda.accountProject.system.config.security;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SecurityConfiguration {

    private final ObjectMapper objectMapper;

    public SecurityConfiguration(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, ClientRegistrationRepository repo) throws Exception {
        http
                .csrf( csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/me").permitAll()
                        .anyRequest().authenticated()
                )




                // authenticationEntryPoint 를 이용해서 시큐리티의 기본 인증 동작을 커스터마이징한다.
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint(objectMapper)))




                .oauth2Login(oauth2 -> {
                    oauth2
                            .defaultSuccessUrl("http://localhost:3000", true)


                            // authorizationEndpoint 를 이용해서 다시 로그인시에도 명확성을 위해 구글 계정 등 로그인 페이지가 나오도록 설정한다.
                            // (원래는 재로그인시 계정 선택 화면도 안뜨고 바로 로그인됨)
                            .authorizationEndpoint(authorization ->
                                    authorization.authorizationRequestResolver(
                                            customAuthorizationRequestResolver(repo)

                                    )
                            );
                });

        return http.build();
    }




    private OAuth2AuthorizationRequestResolver customAuthorizationRequestResolver(
            ClientRegistrationRepository repo) {
        DefaultOAuth2AuthorizationRequestResolver defaultResolver =
                new DefaultOAuth2AuthorizationRequestResolver(repo, "/oauth2/authorization");

        return new OAuth2AuthorizationRequestResolver() {
            @Override
            public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
                OAuth2AuthorizationRequest authRequest = defaultResolver.resolve(request);
                return customizeAuthRequest(authRequest);
            }

            @Override
            public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
                OAuth2AuthorizationRequest authRequest = defaultResolver.resolve(request, clientRegistrationId);
                return customizeAuthRequest(authRequest);
            }

            private OAuth2AuthorizationRequest customizeAuthRequest(OAuth2AuthorizationRequest authRequest) {
                if (authRequest == null) {
                    return null;
                }

                Map<String, Object> extraParams = new HashMap<>(authRequest.getAdditionalParameters());
                extraParams.put("prompt", "consent"); // 또는 "select_account"

                return OAuth2AuthorizationRequest.from(authRequest)
                        .additionalParameters(extraParams)
                        .build();
            }
        };
    }



}
