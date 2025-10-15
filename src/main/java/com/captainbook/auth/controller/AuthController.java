package com.captainbook.auth.controller;

import com.captainbook.auth.domain.RefreshToken;
import com.captainbook.auth.repository.RefreshTokenRepository;
import com.captainbook.auth.security.JwtTokenProvider;
import com.captainbook.auth.service.AuthService;
import com.captainbook.member.domain.Member;
import com.captainbook.member.dto.MemberResponseDto;
import com.captainbook.system.common.response.ResponseData;
import com.captainbook.system.config.security.CustomOAuth2User;
import com.captainbook.system.config.security.CustomUserDetails;
import com.captainbook.system.config.security.CustomUserDetailsService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {


    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final CustomUserDetailsService customUserDetailsService;


    // 목적: 정식 사용자 인증을 위한 맵핑포인트이다. 인증이 성공적이면 유저 정보를 응답한다.
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






    // 목적: jwt refresh 토큰을 이용해 access 토큰을 재발급하는 엔드포인트이다.
    // 현재 여기서 오류가 난다는것은 refresh 토큰이 만료되었을 가능성이 가장 크다.
    @PostMapping("/auth/refresh")
    public ResponseEntity<ResponseData<Map<String, String>>> refreshAccessToken( HttpServletRequest request,
                                                                                 HttpServletResponse response
    ){

        // 1. 쿠키에서 리프레시 토큰 추출
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();

        // 2. 쿠키 배열이 null이 아닌지 확인
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }

        // 3. 리프레시 토큰 존재 여부 확인 및 오류 응답
        if (refreshToken == null) {
            return buildResponse("리프레시 토큰을 찾을 수 없습니다.", HttpStatus.UNAUTHORIZED);
        }



        // 4. 리프레시 토큰 유효성 검증
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            return buildResponse("유효하지 않은 리프레시 토큰입니다.", HttpStatus.UNAUTHORIZED);
        }


        // 5. memberId 추출 및 Redis에서 리프레시 토큰 조회 (현재 redis 키는 memberId 이다)
        Long memberId = Long.parseLong(jwtTokenProvider.getSubject(refreshToken));
        RefreshToken storedToken = refreshTokenRepository.findById(memberId).orElse(null);


        if (storedToken == null || !storedToken.getRefreshToken().equals(refreshToken)) {

            // 탈취된 토큰이나 이미 사용된 토큰 또는 로그아웃된 토큰 등 유효하지 않은 토큰일 것임
            // 정상적이라면 Redis에는 유효한 토큰만 저장해놓는 것이 목적이기 때문이다
            if (storedToken != null) {

                // 보안을 위해 저장소에서 해당 토큰 삭제 처리
                refreshTokenRepository.deleteById(memberId);
            }
            return buildResponse("리프레시 토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED);
        }


        // 6. 새로운 Access Token 및 Refresh Token 발급 (Rotation 전략)
        UserDetails userDetails = customUserDetailsService.loadUserByMemberId(memberId);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        String newAccessToken = jwtTokenProvider.generateAccessToken(authentication);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(authentication);

        // 7. Redis 업데이트: 기존 토큰을 새 토큰으로 업데이트 후 저장
        long expirySeconds = jwtTokenProvider.getRefreshTokenValidity() / 1000;
        storedToken.updateToken(newRefreshToken, expirySeconds);
        refreshTokenRepository.save(storedToken);

        // 8. 새 리프레시 토큰을 새 HttpOnly Cookie로 재설정
        Cookie refreshCookie = new Cookie("refreshToken", newRefreshToken);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(true);
        refreshCookie.setMaxAge((int) expirySeconds);
        refreshCookie.setPath("/");
        response.addCookie(refreshCookie);

        // 9. 새 Access Token 응답
        HttpStatus status = HttpStatus.OK;
        Map<String, String> jwtTokenData = Map.of(
                "accessToken", newAccessToken,
                "message", "토큰이 재발급 되었습니다."
        );


        return ResponseEntity
                .ok(ResponseData.<Map<String, String>>builder()
                        .statusCode(status.value())
                        .data(jwtTokenData)
                        .build());
    }


    // 목적: 엄격한 로그아웃 처리를 위한 맵핑포인트이다.
    // 리프레시 토큰을 Redis 에서도 삭제하고, 응답으로 브라우저 쿠키에서도 리프레시 토큰을 삭제하도록 만든다
    @PostMapping("/auth/logout")
    public ResponseEntity<ResponseData<String>> logout(HttpServletRequest request, HttpServletResponse response) {

        // 요청에 액세스 토큰이 있다면 추출
        String accessToken = extractAccessTokenFromHeader(request);

        // memberId 변수 선언
        String memberId = null;


        // memberId 획득 시도 (redis DB에 만약 존재할 리프레시 토큰을 확실히 삭제하기 위한 재료로)
        if (accessToken != null) {

            // Access Token에서 subject(memberId) 추출
            memberId = jwtTokenProvider.getSubject(accessToken);
        }

        // 만약 Access Token이 없으면 Refresh Token에서 memberId 추출을 시도
        if (memberId == null) {
            String refreshToken = extractRefreshTokenFromCookie(request);
            if (refreshToken != null) {
                memberId = jwtTokenProvider.getSubject(refreshToken);
            }
        }



        if (memberId != null) {
            // memberId를 획득했다면 DB에서 refresh 토큰을 삭제하며 무효화
            authService.deleteRefreshTokenByMemberId(memberId);
        }

        // HttpOnly 쿠키 만료 명령을 전송
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        // 응답에 쿠키를 추가하여 브라우저에 전송
        response.addCookie(cookie);

        // 성공 응답 반환
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseData.<String>builder()
                        .statusCode(HttpStatus.OK.value())
                        .data("로그아웃을 성공적으로 완료하였습니다.")
                        .build());
    }



    // 목적: 시큐리티 필터로 인증 상태만 가볍게 체크할 목적의 맵핑포인트이다.
    @GetMapping("/auth/ping")
    public ResponseEntity<ResponseData<Map<String, String>>> getAuthFetchStatus() {

        Map<String, String> authFetchSuccessData = Map.of(
                "message", "통신 성공"
        );

        return ResponseEntity
                .ok(ResponseData.<Map<String, String>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .data(authFetchSuccessData)
                        .build());
    }




    // 목적: 요청(HttpServletRequest)에서 쿠키를 찾아 리프레시 토큰 값을 추출하는 헬퍼 메서드이다
    private String extractRefreshTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("refreshToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }


    // 목적: 요청(HttpServletRequest)에서 헤더를 찾아 액세스 토큰 값을 추출하는 헬퍼 메서드이다
    private String extractAccessTokenFromHeader(HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

            return authorizationHeader.substring(7);
        }
        return null;
    }


    // 목적: 사용 편의를 위한 공통 응답 포맷 생성 메서드이다
    private ResponseEntity<ResponseData<Map<String, String>>> buildResponse(String message, HttpStatus status) {
        ResponseData<Map<String, String>> resBody = ResponseData.<Map<String, String>>builder()
                .statusCode(status.value())
                .data(Map.of("message", message))
                .build();
        return ResponseEntity.status(status).body(resBody);


    }



//    // csrf 토큰용인데 현재는 사용하지 않으므로 일단 주석처리한다.
//    // 목적: csrf 토큰 쿠키 설정용 엔드포인트이다
//    @GetMapping("/auth/csrf-token")
//    public ResponseEntity<ResponseData<Void>> getCsrfToken(CsrfToken csrfToken) {
//
//        // 이로 인해 csrf 토큰은 자동으로 set cookie된다. (보안상 응답 body에는 토큰을 담지않는다.)
//        csrfToken.getToken();
//
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(
//                        ResponseData.<Void>builder()
//                                .statusCode(HttpStatus.OK.value())
//                                .data(null)
//                                .build()
//                );
//    }








}
