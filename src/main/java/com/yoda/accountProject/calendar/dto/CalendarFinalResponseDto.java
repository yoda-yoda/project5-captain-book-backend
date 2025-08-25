package com.yoda.accountProject.calendar.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CalendarFinalResponseDto {

   private List<CalendarResponseDto> calendarResponseDtoList;
   private Long calendarTotalSum;

   public CalendarFinalResponseDto(List<CalendarResponseDto> calendarResponseDtoList, Long calendarTotalSum) {
       this.calendarResponseDtoList = calendarResponseDtoList;
       this.calendarTotalSum = calendarTotalSum;
   }
}
