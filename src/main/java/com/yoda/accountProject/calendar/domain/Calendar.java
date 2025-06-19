package com.yoda.accountProject.calendar.domain;

import com.yoda.accountProject.calendar.dto.CalendarUpdateDto;
import com.yoda.accountProject.calendarItem.domain.CalendarItem;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private String title;

    private String date;

    @Builder
    public Calendar(String title, String date) {
        this.title = title;
        this.date = date;
    }


    public void updateFromDto(CalendarUpdateDto calendarUpdateDto){
        this.title = calendarUpdateDto.getTitle();
        this.date = calendarUpdateDto.getDate();
    }

}
