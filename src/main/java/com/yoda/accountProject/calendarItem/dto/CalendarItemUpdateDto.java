package com.yoda.accountProject.calendarItem.dto;

import com.yoda.accountProject.calendar.domain.Calendar;
import com.yoda.accountProject.calendarItem.domain.CalendarItem;
import com.yoda.accountProject.calendarItem.domain.Type;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CalendarItemUpdateDto {

    private String itemTitle;
    private Long itemAmount;
    private Type type;

    @Builder
    public CalendarItemUpdateDto(String itemTitle, Long itemAmount, Type type) {
        this.itemTitle = itemTitle;
        this.itemAmount = itemAmount;
        this.type = type;
    }
}
