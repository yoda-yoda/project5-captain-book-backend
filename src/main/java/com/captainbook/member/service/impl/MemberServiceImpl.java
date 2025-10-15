package com.captainbook.member.service.impl;
import com.captainbook.member.domain.Member;
import com.captainbook.member.domain.MemberRole;
import com.captainbook.member.dto.MemberFormRegisterDto;
import com.captainbook.member.dto.MemberResponseDto;
import com.captainbook.member.dto.MemberUpdateDto;
import com.captainbook.member.repository.MemberRepository;
import com.captainbook.member.service.MemberService;
import com.captainbook.system.exception.ExceptionMessage;
import com.captainbook.system.exception.auth.GoogleOAuthException;
import com.captainbook.system.exception.auth.KakaoOAuthException;
import com.captainbook.system.exception.auth.NaverOAuthException;
import com.captainbook.system.exception.member.MemberNotFoundException;
import com.captainbook.system.exception.auth.UserObjectTypeMismatchException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {


    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final String formProvider = "local";

    public boolean isAlreadyFormMember(String userId) {

        // (provider + userId) 조합이 이미 존재하면 유일하지 않으므로 true를 반환한다.
        Optional<Member> entity = memberRepository.findByProviderAndUserId(formProvider, userId);
        return entity.isPresent();

    }



    public MemberResponseDto saveMember(MemberFormRegisterDto memberFormRegisterDto) {

        String encodedPassword = passwordEncoder.encode(memberFormRegisterDto.getPassword());

        // 현재 구현상 폼 회원 등록은 서비스 계층에서 provider에 "local"을 자동으로 설정해준다.
        memberFormRegisterDto.setProvider(formProvider);
        memberFormRegisterDto.setPassword(encodedPassword);
        memberFormRegisterDto.setMemberRole(MemberRole.USER);

        Member entity = MemberFormRegisterDto.toEntity(memberFormRegisterDto);

        Member savedEntity = memberRepository.save(entity);

        return MemberResponseDto.fromEntity(savedEntity);
    }


    // 목적: 오아스 회원 인증 로직에 사용된다.
    // DB에 회원 정보가 있으면 그대로 Member 객체를 반환하여 회원 정보 저장에 사용되고,
    // DB에 없다면 저장한 후에 반환한다.
    public Member saveOAuthMemberFromLoadUser(String provider, Map<String, Object> attributes) {

        switch (provider) {

            case "google":
                return saveGoogleMember(provider, attributes);
            case "kakao":
                return saveKakaoMember(provider, attributes);
            case "naver":
                return saveNaverMember(provider, attributes);
            default:
                throw new UserObjectTypeMismatchException(ExceptionMessage.Auth.USER_OBJECT_TYPE_MISMATCH_ERROR);
        }

    }


    // 구글 멤버 저장 로직
    public Member saveGoogleMember(String provider, Map<String, Object> attributes) {

        try{
            String oAuthUserId = (String) attributes.get("sub");

            // DB 조회
            Optional<Member> optionalMember = memberRepository.findByProviderAndUserId(provider, oAuthUserId);

            // DB에 없다면 회원가입 처리
            if (optionalMember.isEmpty()) {


                // 만약 이름과 이메일이 비공개여서 null 일 경우 오류 방지를 위해 기본값을 설정
                String oAuthUserName = Optional.ofNullable((String) attributes.get("name"))
                        .orElse(getOAuthDefaultUsername(provider, oAuthUserId));

                String oAuthUserEmail = Optional.ofNullable((String) attributes.get("email"))
                        .orElse(getOAuthDefaultEmail(provider, oAuthUserId));


                // OAuth 계정은 password가 필요없고, 성능 개선을 위해 더미 값을 넣지않고 null처리를 하였다.
                Member newMember = Member.builder()
                        .name(oAuthUserName)
                        .email(oAuthUserEmail)
                        .provider(provider)
                        .userId(oAuthUserId)
                        .password(null)
                        .memberRole(MemberRole.USER)
                        .build();

                return memberRepository.save(newMember);
            } else {
                return optionalMember.get();
            }
        } catch (ClassCastException | NullPointerException e) {
            throw new GoogleOAuthException(ExceptionMessage.Auth.GOOGLE_OAUTH_ERROR, e);
        }
    }

    // 카카오 멤버 저장 로직(현재 저장 로직만 구현해놓았다)
    public Member saveKakaoMember(String provider, Map<String, Object> attributes) {

        try {
            String oAuthUserId = (String) attributes.get("id");

            // DB 조회
            Optional<Member> optionalMember = memberRepository.findByProviderAndUserId(provider, oAuthUserId);

            // DB에 없다면 회원가입 처리
            if (optionalMember.isEmpty()) {

                // 만약 이름과 이메일이 비공개여서 null 일 경우 오류 방지를 위해 기본값을 설정
                String oAuthUserName = Optional.ofNullable((String) (((Map<String, Object>) attributes.get("properties")).get("nickname")))
                        .orElse(getOAuthDefaultUsername(provider, oAuthUserId));

                String oAuthUserEmail = Optional.ofNullable((String) (((Map<String, Object>) attributes.get("kakao_account")).get("email")))
                        .orElse(getOAuthDefaultEmail(provider, oAuthUserId));

                Member newMember = Member.builder()
                        .name(oAuthUserName)
                        .email(oAuthUserEmail)
                        .provider(provider)
                        .userId(oAuthUserId)
                        .password(null)
                        .memberRole(MemberRole.USER)
                        .build();

                return memberRepository.save(newMember);
            } else {
                return optionalMember.get();
            }
        } catch (ClassCastException | NullPointerException e) {
            throw new KakaoOAuthException(ExceptionMessage.Auth.KAKAO_OAUTH_ERROR, e);
        }
    }


    // 네이버 멤버 저장 로직(현재 저장 로직만 구현해놓았다)
    public Member saveNaverMember(String provider, Map<String, Object> attributes) {

        try {
            String oAuthUserId = (String) (((Map<String, Object>) attributes.get("response")).get("id"));

            // DB 조회
            Optional<Member> optionalMember = memberRepository.findByProviderAndUserId(provider, oAuthUserId);

            // DB에 없다면 회원가입 처리
            if (optionalMember.isEmpty()) {

                // 만약 이름과 이메일이 비공개여서 null 일 경우 오류 방지를 위해 기본값을 설정
                String oAuthUserName = Optional.ofNullable((String) (((Map<String, Object>) attributes.get("response")).get("nickname")))
                        .orElse(getOAuthDefaultUsername(provider, oAuthUserId));

                String oAuthUserEmail = Optional.ofNullable((String) (((Map<String, Object>) attributes.get("response")).get("email")))
                        .orElse(getOAuthDefaultEmail(provider, oAuthUserId));

                Member newMember = Member.builder()
                        .name(oAuthUserName)
                        .email(oAuthUserEmail)
                        .provider(provider)
                        .userId(oAuthUserId)
                        .password(null)
                        .memberRole(MemberRole.USER)
                        .build();

                return memberRepository.save(newMember);
            } else {
                return optionalMember.get();
            }

        } catch (ClassCastException | NullPointerException e) {
            throw new NaverOAuthException(ExceptionMessage.Auth.NAVER_OAUTH_ERROR, e);
        }

    }

    // 오아스 유저 기본 이름 설정을 위한 헬퍼 메서드이다
    public String getOAuthDefaultUsername(String provider, String oAuthUserId) {

        // 만약 ID가 8자리 이하일 경우에는 오류 방지를 위해 oAuthUserId를 그대로 할당
        String suffixUserId = oAuthUserId.length() > 8 ? oAuthUserId.substring(0, 8) : oAuthUserId;
        return "사용자_" + provider + suffixUserId;
    }

    // 오아스 유저 기본 이메일 설정을 위한 헬퍼 메서드이다
    public String getOAuthDefaultEmail(String provider, String oAuthUserId) {
        String suffixUserId = oAuthUserId.length() > 8 ? oAuthUserId.substring(0, 8) : oAuthUserId;
        return provider.toLowerCase() + "_" + suffixUserId + "@" + provider.toLowerCase() + ".com";
    }




    public List<MemberResponseDto> getAllMember() {

        List<MemberResponseDto> allMemberDtoList = new ArrayList<>();

        // 여기서 findAll을 jpql로 짜야 N+1 예방되긴할듯 아무튼 ?(복사한거라 체크필요)
        List<Member> MemberList = memberRepository.findAll();

        // 데이터가 없다면 빈 리스트 반환
        if(MemberList.isEmpty()){
            return allMemberDtoList;
        }

        for (Member member : MemberList) {

            MemberResponseDto memberResponseDto = MemberResponseDto.fromEntity(member);

            allMemberDtoList.add( memberResponseDto );
        }


        return allMemberDtoList;

    }


    public MemberResponseDto getMemberDtoById(Long memberId) {

        Member findEntity = memberRepository.findById(memberId)
                .orElseThrow( () -> new MemberNotFoundException(ExceptionMessage.Member.MEMBER_NOT_FOUND_ERROR));

        return MemberResponseDto.fromEntity(findEntity);
    }


    public Member getMemberEntityById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow( () -> new MemberNotFoundException(ExceptionMessage.Member.MEMBER_NOT_FOUND_ERROR));
    }


    @Transactional
    public void updateMember(Long memberId, MemberUpdateDto memberUpdateDto) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow( () -> new MemberNotFoundException(ExceptionMessage.Member.MEMBER_NOT_FOUND_ERROR));

        member.updateFromDto(memberUpdateDto);
    }



    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }



}
