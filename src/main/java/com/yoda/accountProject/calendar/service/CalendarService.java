package com.yoda.accountProject.calendar.service;

import com.yoda.accountProject.calendar.domain.Calendar;
import com.yoda.accountProject.calendar.dto.CalendarRequestDto;
import com.yoda.accountProject.calendar.dto.CalendarResponseDto;
import com.yoda.accountProject.calendar.dto.CalendarUpdateDto;
import java.util.List;

public interface CalendarService {


    CalendarResponseDto saveCalendar(CalendarRequestDto calendarRequestDto);
    List<CalendarResponseDto> getAllCalendar();
    CalendarResponseDto getCalendarDtoById(Long calendarId);
    Calendar getCalendarEntityById(Long calendarId);
    void updateCalendar(Long calendarId, CalendarUpdateDto calendarUpdateDto);
    void deleteCalendar(Long calendarId);




}



