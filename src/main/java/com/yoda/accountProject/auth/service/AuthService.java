package com.yoda.accountProject.auth.service;


import org.springframework.security.oauth2.core.user.OAuth2User;

public interface AuthService {

    Long getCurrentMemberId (Object principal);

}



