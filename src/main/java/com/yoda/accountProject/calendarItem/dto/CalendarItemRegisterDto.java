package com.yoda.accountProject.calendarItem.dto;

import com.yoda.accountProject.calendar.domain.Calendar;
import com.yoda.accountProject.calendarItem.domain.CalendarItem;
import com.yoda.accountProject.calendarItem.domain.Type;
import com.yoda.accountProject.itemType.domain.ItemType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CalendarItemRegisterDto {

    private String itemTitle;
    private Long itemAmount;
    private Type type;

    @Builder
    public CalendarItemRegisterDto(String itemTitle, Long itemAmount, Type type) {
        this.itemTitle = itemTitle;
        this.itemAmount = itemAmount;
        this.type = type;
    }

    public static CalendarItem toEntity(CalendarItemRegisterDto calendarItemRequestDto, Calendar calendar, ItemType itemType){

        return CalendarItem.builder()
                .calendar(calendar)
                .itemType(itemType)
                .itemTitle(calendarItemRequestDto.getItemTitle())
                .itemAmount(calendarItemRequestDto.getItemAmount())
                .build();
    }


}
