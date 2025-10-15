package com.captainbook.member.service;

import com.captainbook.member.domain.Member;
import com.captainbook.member.dto.MemberFormRegisterDto;
import com.captainbook.member.dto.MemberResponseDto;
import com.captainbook.member.dto.MemberUpdateDto;
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



