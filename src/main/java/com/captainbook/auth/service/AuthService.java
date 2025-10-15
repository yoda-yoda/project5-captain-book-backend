package com.captainbook.auth.service;


// 서비스 계층의 인터페이스이다

public interface AuthService {

    Long getCurrentMemberId (Object principal);
    void deleteRefreshTokenByMemberId(String memberId);

}



