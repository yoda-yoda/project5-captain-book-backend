package com.yoda.accountProject.calendarItem.dto;

import com.yoda.accountProject.calendar.domain.Calendar;
import com.yoda.accountProject.calendarItem.domain.CalendarItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CalendarItemUpdateDto {

    private String itemTitle;
    private String itemAmount;


}
