package com.yoda.accountProject.calendarItem.dto;

import com.yoda.accountProject.calendar.domain.Calendar;
import com.yoda.accountProject.calendarItem.domain.CalendarItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CalendarItemRegisterDto {

    private String itemTitle;
    private String itemAmount;

    public static CalendarItem toEntity(CalendarItemRegisterDto calendarItemRequestDto, Calendar calendar){

        return CalendarItem.builder()
                .calendar(calendar)
                .itemTitle(calendarItemRequestDto.getItemTitle())
                .itemAmount(calendarItemRequestDto.getItemAmount())
                .build();
    }


}
