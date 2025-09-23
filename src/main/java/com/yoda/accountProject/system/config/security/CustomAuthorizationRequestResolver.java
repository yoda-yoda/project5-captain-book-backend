package com.yoda.accountProject.system.config.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import java.util.HashMap;
import java.util.Map;


// authorizationEndpoint 를 이용해서 다시 로그인시에도 명확한 인식을 위해 로그인 페이지가 나오도록 설정하기 위한 custom Resolver 클래스이다.
public class CustomAuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {

    private final DefaultOAuth2AuthorizationRequestResolver defaultResolver;

    public CustomAuthorizationRequestResolver(ClientRegistrationRepository repo) {
        this.defaultResolver = new DefaultOAuth2AuthorizationRequestResolver(repo, "/oauth2/authorization");
    }

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
}
