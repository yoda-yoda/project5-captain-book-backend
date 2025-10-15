package com.captainbook.member.controller;

import com.captainbook.member.dto.MemberFormRegisterDto;
import com.captainbook.member.dto.MemberResponseDto;
import com.captainbook.member.dto.MemberUpdateDto;
import com.captainbook.member.service.MemberService;
import com.captainbook.system.common.response.ResponseData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;



    // 목적: 폼회원 가입시 중복 아이디가 있는지 체크한다
    @GetMapping("/members/exits")
    public ResponseEntity<ResponseData<Boolean>> existsMemberRead(@RequestParam("userId") String userId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseData.<Boolean>builder()
                                .statusCode(HttpStatus.OK.value())
                                .data(memberService.isAlreadyFormMember(userId))
                                .build()
                );
    }


    @GetMapping("/members")
    public ResponseEntity<ResponseData<List<MemberResponseDto>>> allMemberRead(HttpServletRequest request) {

        List<MemberResponseDto> memberResponseDtoList = memberService.getAllMember();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseData.<List<MemberResponseDto>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .data(memberResponseDtoList)
                        .build()
                );
    }



    @GetMapping("/members/{memberId}")
    public ResponseEntity<ResponseData<MemberResponseDto>> memberRead(@PathVariable Long memberId) {

        MemberResponseDto dto = memberService.getMemberDtoById(memberId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseData.<MemberResponseDto>builder()
                                .statusCode(HttpStatus.OK.value())
                                .data(dto)
                                .build()
                );
    }


    // 목적: 폼회원 가입 로직이다
    @PostMapping("/members")
    public ResponseEntity<ResponseData<MemberResponseDto>> memberCreate
            (@RequestBody @Valid MemberFormRegisterDto memberFormRegisterDto){

        MemberResponseDto res = memberService.saveMember(memberFormRegisterDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ResponseData.<MemberResponseDto>builder()
                                .statusCode(HttpStatus.CREATED.value())
                                .data(res)
                                .build()
                );
    }



    @PutMapping("/members/update/{memberId}")
    public ResponseEntity<ResponseData<Void>> memberUpdate(
            @PathVariable Long memberId,
            @RequestBody @Valid MemberUpdateDto memberUpdateDto){

        memberService.updateMember(memberId, memberUpdateDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseData.<Void>builder()
                                .statusCode(HttpStatus.OK.value())
                                .build()
                );
    }



    @DeleteMapping("/members/delete/{memberResponseDtoId}")
    public ResponseEntity<ResponseData<Void>> memberDelete(@PathVariable Long memberResponseDtoId){

        memberService.deleteMember(memberResponseDtoId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(
                        ResponseData.<Void>builder()
                                .statusCode(HttpStatus.NO_CONTENT.value())
                                .build()
                );
    }



}
