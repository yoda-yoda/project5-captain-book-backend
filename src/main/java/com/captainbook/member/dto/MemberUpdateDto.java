package com.captainbook.member.dto;

import com.captainbook.member.domain.MemberRole;
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
public class MemberUpdateDto {

    @NotNull
    @Size(max = 50, message = "이름 항목은 50자 이하여야 합니다.")
    private String name;

    @Size(max = 100, message = "이메일은 100자 이하여야 합니다.")
    private String email;

    @Size(max = 255, message = "비밀번호는 255자 이하여야 합니다.")
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull
    private MemberRole memberRole;


    @Builder
    public MemberUpdateDto(String name, String email, String password, MemberRole memberRole) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.memberRole = memberRole;
    }

}
