package com.yoda.accountProject.auth.controller;

import com.yoda.accountProject.system.common.response.ResponseData;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {


    @GetMapping("/auth/me")
    public ResponseEntity<ResponseData<?>> getCurrentUser(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(
                            ResponseData.<String>builder()
                                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                                    .data("Not authenticated")
                                    .build());
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseData.<Map<String, Object>>builder()
                                .statusCode(HttpStatus.OK.value())
                                .data(principal.getAttributes())
                                .build()
                );
    }


    // csrf 토큰 쿠키 설정용 엔드포인트
    @GetMapping("/auth/csrf-token")
    public ResponseEntity<ResponseData<Void>> getCsrfToken(CsrfToken csrfToken) {

        // 이로 인해 csrf 토큰은 자동으로 set cookie된다. (보안상 응답 body에는 토큰을 담지않는다.)
        csrfToken.getToken();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseData.<Void>builder()
                                .statusCode(HttpStatus.OK.value())
                                .data(null)
                                .build()
                );
    }










//    @GetMapping("/check-auth")
//    public ResponseEntity<ResponseData<String>> checkAuth(Authentication authentication) {
//        if (session == null || session.isNew()) {
//
//            return ResponseEntity
//                    .status(HttpStatus.UNAUTHORIZED)
//                    .body(
//                            ResponseData.<String>builder()
//                                    .statusCode(HttpStatus.UNAUTHORIZED.value())
//                                    .data("No session or new session")
//                                    .build()
//                    );
//        }
//
//
//
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(
//                        ResponseData.<String>builder()
//                                .statusCode(HttpStatus.OK.value())
//                                .data("Session exists: " + session.getId())
//                                .build()
//                );
//
//
//    }





    @GetMapping("/check-session")
    public ResponseEntity<ResponseData<String>> checkSession(HttpSession session) {
        if (session == null || session.isNew()) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(
                            ResponseData.<String>builder()
                                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                                    .data("No session or new session")
                                    .build()
                    );
        }



        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseData.<String>builder()
                                .statusCode(HttpStatus.OK.value())
                                .data("Session exists: " + session.getId())
                                .build()
                );


    }





}
