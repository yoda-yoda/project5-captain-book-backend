package com.yoda.accountProject.member.dto;
import com.yoda.accountProject.member.domain.Member;
import com.yoda.accountProject.member.domain.MemberRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberCommonRegisterDto {

    @NotNull
    @Size(max = 50, message = "이름은 50자 이하여야 합니다.")
    private String name;

    @Size(max = 100, message = "이메일은 100자 이하여야 합니다.")
    private String email;

    @NotNull
    private String provider;

    // OAuth 회원인 경우 userId는 곧 providerId를 의미한다.
    @NotNull
    @Size(max = 100, message = "아이디는 100자 이하여야 합니다.")
    private String userId;

    // OAuth 회원인 경우 password 는 nullable 하다.
    @Size(max = 255, message = "비밀번호는 255자 이하여야 합니다.")
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull
    private MemberRole memberRole;



    public static Member toEntity(MemberCommonRegisterDto memberRegisterDto){
        return Member.builder()
                .name(memberRegisterDto.getName())
                .email(memberRegisterDto.getEmail())
                .provider(memberRegisterDto.getProvider())
                .userId(memberRegisterDto.getUserId())
                .password(memberRegisterDto.getPassword())
                .memberRole(memberRegisterDto.getMemberRole())
                .build();
    }

    @Builder
    public MemberCommonRegisterDto(String name, String email, String provider, String userId, String password, MemberRole memberRole) {
        this.name = name;
        this.email = email;
        this.provider = provider;
        this.userId = userId;
        this.password = password;
        this.memberRole = memberRole;
    }


}
