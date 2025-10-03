package com.yoda.accountProject.member.service;

import com.yoda.accountProject.member.domain.Member;
import com.yoda.accountProject.member.dto.MemberFormRegisterDto;
import com.yoda.accountProject.member.dto.MemberResponseDto;
import com.yoda.accountProject.member.dto.MemberUpdateDto;
import java.util.List;
import java.util.Map;

public interface MemberService {

    boolean isAlreadyFormMember(String userId);
    MemberResponseDto saveMember(MemberFormRegisterDto memberFormRegisterDto);
    Member saveOAuthMemberFromLoadUser(String provider, Map<String, Object> attributes);
    Member saveGoogleMember(String provider, Map<String, Object> attributes);
    Member saveKakaoMember(String provider, Map<String, Object> attributes);
    Member saveNaverMember(String provider, Map<String, Object> attributes);
    List<MemberResponseDto> getAllMember();
    MemberResponseDto getMemberDtoById(Long memberId);
    Member getMemberEntityById(Long memberId);
    void updateMember(Long memberId, MemberUpdateDto memberUpdateDto);
    void deleteMember(Long memberId);


}



