package com.yoda.accountProject.calendar.service;

import com.yoda.accountProject.calendar.domain.Calendar;
import com.yoda.accountProject.calendar.dto.CalendarRequestDto;
import com.yoda.accountProject.calendar.dto.CalendarResponseDto;
import com.yoda.accountProject.calendar.dto.CalendarUpdateDto;
import java.util.List;

public interface CalendarService {


    CalendarResponseDto saveCalendar(CalendarRequestDto calendarRequestDto, Long currentMemberId);
    List<CalendarResponseDto> getAllCalendar(Long currentMemberId);
    Long getTotalCalendarAmountSum(List<CalendarResponseDto> allCalendarDtoList);
    CalendarResponseDto getCalendarDtoByIdAndMemberId(Long calendarId, Long currentMemberId);
    Calendar getCalendarEntityById(Long calendarId);
    void updateCalendar(Long calendarId, CalendarUpdateDto calendarUpdateDto, Long memberId);
    void deleteCalendar(Long calendarId, Long currentMemberId);




}



