package com.yoda.accountProject.member.domain;

import com.yoda.accountProject.calendar.domain.Calendar;
import com.yoda.accountProject.calendarItem.domain.CalendarItem;
import com.yoda.accountProject.member.dto.MemberUpdateDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"provider", "userId"})
)
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Calendar> calendarList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CalendarItem> calendarItemList = new ArrayList<>();

    @Column(nullable = false, length = 50)
    private String name;

    // email에 unique 설정을 안한 이유는 Oauth Provider 종류가 다양하여 email은 중복 가능하기 때문이다.
    // 따라서 유니크 설정은 'provider'와 'userId'로 복합유니크 설정을 하였다.
    @Column(nullable = true, length = 100)
    private String email;

    // 현재 구현상 OAuth 로그인에서는 해당 provider가 담기고 폼로그인 에서는 'local'이 담긴다.
    @Column(nullable = false)
    private String provider;

    // Form 회원인 경우 로그인Id를 의미한다.
    // OAuth 회원인 경우 userId는 곧 providerId가 된다.
    @Column(nullable = false, length = 100)
    private String userId;

    // OAuth 와 Form 로그인을 하나의 엔티티로 통합하고,
    // OAuth의 경우 password가 필요없어서 저장 성능 개선을 위해 nullable = true로 설정하였다.
    @Column(nullable = true, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole memberRole;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;


    @Builder
    public Member(String name, String email, String provider, String userId, String password, MemberRole memberRole) {
        this.name = name;
        this.email = email;
        this.provider = provider;
        this.userId = userId;
        this.password = password;
        this.memberRole = memberRole;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }


    public void updateFromDto(MemberUpdateDto memberUpdateDto){
        this.name = memberUpdateDto.getName();
        this.email = memberUpdateDto.getEmail();
        this.password = memberUpdateDto.getPassword();
        this.memberRole = memberUpdateDto.getMemberRole();
        this.updatedAt = LocalDateTime.now();
    }

}
