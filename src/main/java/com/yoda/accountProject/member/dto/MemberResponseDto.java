package com.yoda.accountProject.member.dto;

import com.yoda.accountProject.member.domain.Member;
import com.yoda.accountProject.member.domain.MemberRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class MemberResponseDto {

    private Long id;

    private String name;

    private String email;

    private String provider;

    private String userId;

    private MemberRole memberRole;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    public static MemberResponseDto fromEntity(Member member){
        return MemberResponseDto.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .provider(member.getProvider())
                .userId(member.getUserId())
                .memberRole(member.getMemberRole())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .build();
    }

    @Builder
    public MemberResponseDto(Long id, String name, String email, String provider, String userId, MemberRole memberRole,
                             LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.provider = provider;
        this.userId = userId;
        this.memberRole = memberRole;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

    }
}
