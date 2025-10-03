package com.yoda.accountProject.member.dto;
import com.yoda.accountProject.member.domain.Member;
import com.yoda.accountProject.member.domain.MemberRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberFormRegisterDto {

    @NotNull
    @Size(max = 20, message = "이름은 20글자 이하여야 합니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,20}$", message = "이름: 2~20개의 완성형 글자입니다. 특수문자와 공백 등은 제외됩니다.")
    private String name;

    // 이메일 입력값은 조건부로 검증하기 때문에 밑의 @AssertTrue를 활용한다.
    @Size(max = 100, message = "이메일은 100글자 이하여야 합니다.")
    private String email;

    private String provider;

    // OAuth 회원인 경우 userId는 곧 providerId를 의미한다.
    @NotNull
    @Size(max = 100, message = "아이디는 100글자 이하여야 합니다.")
    @Pattern(regexp = "^[a-z][a-z0-9_]{4,19}$",
            message = "아이디: 5~20글자, 영어 소문자, 숫자, 언더바('_')가 가능합니다. 첫 글자는 영어입니다.")
    private String userId;


    @NotNull
    @Size(max = 255, message = "비밀번호는 255글자 이하여야 합니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{7,20}$",
            message = "비밀번호: 7~20글자, 영문 최소 1개, 숫자 최소 1개를 포함합니다.")
    private String password;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;


    // email 값을 입력하면 정규식을 검증하고, 입력하지 않으면 통과된다.
    @AssertTrue(message = "올바른 이메일 형식을 입력해주세요.")
    private boolean isEmailValid() {
        if (email == null || email.trim().isEmpty()) {
            return true;
        }
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }


    // email 입력 값이 없다면 DB에 null로 통일하여 저장하기위해 toEntity 메서드 자체에서 null로 변환한다.
    public static Member toEntity(MemberFormRegisterDto memberFormRegisterDto){
        return Member.builder()
                .name(memberFormRegisterDto.getName())
                .email( (memberFormRegisterDto.getEmail() == null ||
                        memberFormRegisterDto.getEmail().isEmpty() ) ? null : memberFormRegisterDto.getEmail() )
                .provider(memberFormRegisterDto.getProvider())
                .userId(memberFormRegisterDto.getUserId())
                .password(memberFormRegisterDto.getPassword())
                .memberRole(memberFormRegisterDto.getMemberRole())
                .build();
    }



    @Builder
    public MemberFormRegisterDto(String name, String email, String provider, String userId, String password, MemberRole memberRole) {
        this.name = name;
        this.email = email;
        this.provider = provider;
        this.userId = userId;
        this.password = password;
        this.memberRole = memberRole;
    }

}
