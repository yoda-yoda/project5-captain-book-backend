package com.yoda.accountProject.auth.controller;

import com.yoda.accountProject.system.common.response.ResponseData;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
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
