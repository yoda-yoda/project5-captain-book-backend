package com.yoda.accountProject.calendar.service;

import com.yoda.accountProject.calendar.domain.Calendar;
import com.yoda.accountProject.calendar.dto.CalendarRequestDto;
import com.yoda.accountProject.calendar.dto.CalendarResponseDto;
import com.yoda.accountProject.calendar.dto.CalendarUpdateDto;
import com.yoda.accountProject.calendarItem.dto.CalendarItemResponseDto;
import com.yoda.accountProject.calendarItem.dto.CalendarItemTotalAmountDto;

import java.util.List;

public interface CalendarService {


    CalendarResponseDto saveCalendar(CalendarRequestDto calendarRequestDto);
    List<CalendarResponseDto> getAllCalendar();
    Long getTotalCalendarAmountSum(List<CalendarResponseDto> allCalendarDtoList);
    CalendarResponseDto getCalendarDtoById(Long calendarId);
    Calendar getCalendarEntityById(Long calendarId);
    void updateCalendar(Long calendarId, CalendarUpdateDto calendarUpdateDto);
    void deleteCalendar(Long calendarId);




}



