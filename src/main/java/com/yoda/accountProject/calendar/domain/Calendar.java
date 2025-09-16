package com.yoda.accountProject.calendar.domain;

import com.yoda.accountProject.calendar.dto.CalendarUpdateDto;
import com.yoda.accountProject.calendarItem.domain.CalendarItem;
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

    @Column(nullable = false, length = 35)
    private String title;

    // 현재 애플리케이션 단계에서는 9999-12-31 이하 제약을 어노테이션으로 걸기 어렵기때문에,
    // schema.sql로 9999-12-31 이하의 DB 컬럼 제약을 걸어놓은 상태이다.
    @Column(nullable = false)
    private LocalDate date;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public Calendar(String title, LocalDate date) {
        this.title = title;
        this.date = date;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }


    public void updateFromDto(CalendarUpdateDto calendarUpdateDto){
        this.title = calendarUpdateDto.getTitle();
        this.date = calendarUpdateDto.getDate();
        this.updatedAt = LocalDateTime.now();
    }

}
