package com.captainbook.calendaritem.dto;

import com.captainbook.calendar.dto.CalendarResponseDto;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class CalendarItemFinalResponseDto {

    private CalendarResponseDto calendarResponseDto;
    private List<CalendarItemResponseDto> calendarItemResponseDtoList;
    private CalendarItemTotalAmountDto totalAmountDto;

    public CalendarItemFinalResponseDto(CalendarResponseDto calendarResponseDto, List<CalendarItemResponseDto> calendarItemResponseDtoList, CalendarItemTotalAmountDto totalAmountDto) {
        this.calendarResponseDto = calendarResponseDto;
        this.calendarItemResponseDtoList = calendarItemResponseDtoList;
        this.totalAmountDto = totalAmountDto;
    }
}
