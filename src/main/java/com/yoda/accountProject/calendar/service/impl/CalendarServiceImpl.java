package com.yoda.accountProject.calendar.service.impl;

import com.yoda.accountProject.calendar.domain.Calendar;
import com.yoda.accountProject.calendar.dto.CalendarRequestDto;
import com.yoda.accountProject.calendar.dto.CalendarResponseDto;
import com.yoda.accountProject.calendar.dto.CalendarUpdateDto;
import com.yoda.accountProject.calendar.repository.CalendarRepository;
import com.yoda.accountProject.calendar.service.CalendarService;
import com.yoda.accountProject.system.exception.ExceptionMessage;
import com.yoda.accountProject.system.exception.calendar.CalendarNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService {

    private final CalendarRepository calendarRepository;


    public CalendarResponseDto saveCalendar(CalendarRequestDto calendarRequestDto) {

        Calendar entity = CalendarRequestDto.toEntity(calendarRequestDto);
        Calendar savedEntity = calendarRepository.save(entity);

        return CalendarResponseDto.fromEntity(savedEntity);
    }


    public List<CalendarResponseDto> getAllCalendar() {

        List<CalendarResponseDto> allCalendarDtoList = new ArrayList<>();

        List<Calendar> calendarList = calendarRepository.findAll();

        // 데이터가 없다면 빈 리스트 반환
        if(calendarList.isEmpty()){
            return allCalendarDtoList;
        }

        for (Calendar calendar : calendarList) {
            allCalendarDtoList.add(CalendarResponseDto.fromEntity(calendar));
        }

        return allCalendarDtoList;
    }


    public CalendarResponseDto getCalendarDtoById(Long calendarId) {

        Calendar findEntity = calendarRepository.findById(calendarId)
                .orElseThrow( () -> new CalendarNotFoundException(ExceptionMessage.Calendar.CALENDAR_NOT_FOUND_ERROR));

        return CalendarResponseDto.fromEntity(findEntity);
    }

    public Calendar getCalendarEntityById(Long calendarId){
        return calendarRepository.findById(calendarId)
                .orElseThrow( () -> new CalendarNotFoundException(ExceptionMessage.Calendar.CALENDAR_NOT_FOUND_ERROR));
    }


    @Transactional
    public void updateCalendar(Long calendarId, CalendarUpdateDto calendarUpdateDto) {

        Calendar calendar = calendarRepository.findById(calendarId)
                .orElseThrow(() -> new CalendarNotFoundException(ExceptionMessage.Calendar.CALENDAR_NOT_FOUND_ERROR));

        calendar.updateFromDto(calendarUpdateDto);
    }



    public void deleteCalendar(Long calendarId) {

        calendarRepository.deleteById(calendarId);

    }




}
