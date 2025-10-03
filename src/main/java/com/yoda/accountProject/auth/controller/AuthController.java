package com.yoda.accountProject.auth.controller;

import com.yoda.accountProject.member.domain.Member;
import com.yoda.accountProject.member.dto.MemberResponseDto;
import com.yoda.accountProject.system.common.response.ResponseData;
import com.yoda.accountProject.system.config.security.CustomOAuth2User;
import com.yoda.accountProject.system.config.security.CustomUserDetails;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {



    @GetMapping("/auth/me")
    public ResponseEntity<ResponseData<?>> getCurrentUser(Authentication authentication) {

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken ) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ResponseData.<String>builder()
                            .statusCode(HttpStatus.UNAUTHORIZED.value())
                            .data("인증에 실패하였습니다.")
                            .build());
        }



        Object principal = authentication.getPrincipal();


        Member memberEntity = null;

        if (principal instanceof CustomUserDetails userDetails) {
            memberEntity = userDetails.getMemberEntity();
        } else if (principal instanceof CustomOAuth2User oAuth2User) {
            memberEntity = oAuth2User.getMemberEntity();
        }

        if (memberEntity == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ResponseData.<String>builder()
                            .statusCode(HttpStatus.UNAUTHORIZED.value())
                            .data("지원하지 않는 인증 타입입니다.")
                            .build());
        }

        MemberResponseDto resDto = MemberResponseDto.fromEntity(memberEntity);


        return ResponseEntity
                .ok(ResponseData.<MemberResponseDto>builder()
                        .statusCode(HttpStatus.OK.value())
                        .data(resDto)
                        .build());
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
