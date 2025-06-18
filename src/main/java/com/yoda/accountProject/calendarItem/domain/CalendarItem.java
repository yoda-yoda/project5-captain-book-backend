package com.yoda.accountProject.calendarItem.domain;

import com.yoda.accountProject.calendar.domain.Calendar;
import com.yoda.accountProject.calendarItem.dto.CalendarItemUpdateDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CalendarItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_id", nullable = false)
    private Calendar calendar;

    private String itemTitle;

    private String itemAmount;

    @Builder
    public CalendarItem(Calendar calendar, String itemTitle, String itemAmount) {
        this.calendar = calendar;
        this.itemTitle = itemTitle;
        this.itemAmount = itemAmount;
    }

    public void updateFromDto(CalendarItemUpdateDto calendarItemUpdateDto){
        this.itemTitle = calendarItemUpdateDto.getItemTitle();
        this.itemAmount = calendarItemUpdateDto.getItemAmount();
    }


}
