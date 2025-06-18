package com.yoda.accountProject.calendarItem.dto;

import com.yoda.accountProject.calendarItem.domain.CalendarItem;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalendarItemResponseDto {

    private Long id;
    private String itemTitle;
    private String itemAmount;


    @Builder
    public CalendarItemResponseDto(Long id, String itemTitle, String itemAmount) {
        this.id = id;
        this.itemTitle = itemTitle;
        this.itemAmount = itemAmount;
    }


    public static CalendarItemResponseDto fromEntity(CalendarItem calendarItem){
        return CalendarItemResponseDto.builder()
                .id(calendarItem.getId())
                .itemTitle(calendarItem.getItemTitle())
                .itemAmount(calendarItem.getItemAmount())
                .build();
    }



}
