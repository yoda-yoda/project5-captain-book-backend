package com.yoda.accountProject.calendar.domain;

import com.yoda.accountProject.calendar.dto.CalendarUpdateDto;
import com.yoda.accountProject.calendarItem.domain.CalendarItem;
import com.yoda.accountProject.member.domain.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Calendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "calendar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CalendarItem> calendarItemList = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;


    @Column(nullable = false, length = 35)
    private String title;

    // 현재 애플리케이션 단계보다 DB차원의 제약을 위해서 schema.sql로 9999-12-31 이하라는 DB 컬럼 제약을 걸어놓은 상태이다.
    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Calendar(String title, LocalDate date, Member member) {
        this.title = title;
        this.date = date;
        this.member = member;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }


    public void updateFromDto(CalendarUpdateDto calendarUpdateDto){
        this.title = calendarUpdateDto.getTitle();
        this.date = calendarUpdateDto.getDate();
        this.updatedAt = LocalDateTime.now();
    }

}
