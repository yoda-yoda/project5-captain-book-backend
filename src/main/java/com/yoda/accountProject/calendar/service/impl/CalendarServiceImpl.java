package com.yoda.accountProject.calendar.service.impl;

import com.yoda.accountProject.calendar.domain.Calendar;
import com.yoda.accountProject.calendar.dto.CalendarRequestDto;
import com.yoda.accountProject.calendar.dto.CalendarResponseDto;
import com.yoda.accountProject.calendar.dto.CalendarUpdateDto;
import com.yoda.accountProject.calendar.repository.CalendarRepository;
import com.yoda.accountProject.calendar.service.CalendarService;
import com.yoda.accountProject.calendarItem.domain.CalendarItem;
import com.yoda.accountProject.calendarItem.dto.CalendarItemResponseDto;
import com.yoda.accountProject.calendarItem.dto.CalendarItemTotalAmountDto;
import com.yoda.accountProject.calendarItem.service.CalendarItemService;
import com.yoda.accountProject.system.exception.ExceptionMessage;
import com.yoda.accountProject.system.exception.calendar.CalendarNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService {

    private final CalendarRepository calendarRepository;
    private final CalendarItemService calendarItemService;


    public CalendarResponseDto saveCalendar(CalendarRequestDto calendarRequestDto) {

        Calendar entity = CalendarRequestDto.toEntity(calendarRequestDto);
        Calendar savedEntity = calendarRepository.save(entity);

        return CalendarResponseDto.fromEntity(savedEntity);
    }


    public List<CalendarResponseDto> getAllCalendar() {

        List<CalendarResponseDto> allCalendarDtoList = new ArrayList<>();

        // 여기서 findAll을 jpql로 짜야 N+1 예방되긴할듯 아무튼
        List<Calendar> calendarList = calendarRepository.findAll();

        // 데이터가 없다면 빈 리스트 반환
        if(calendarList.isEmpty()){
            return allCalendarDtoList;
        }

        // 이안에 getCalendarItemList 할때 조인페치 쿼리작성하기. 왜냐하면 한번의 쿼리로 필요한 데이터를 객체에 모두 할당하기위함
        for (Calendar calendar : calendarList) {

            List<CalendarItem> calendarItemList = calendar.getCalendarItemList();

            CalendarResponseDto calendarResponseDto = CalendarResponseDto.fromEntity(calendar);

            if ( calendarItemList.isEmpty() ) {
                allCalendarDtoList.add( calendarResponseDto );
                continue;
            }

            CalendarItemTotalAmountDto totalAmountDto = calendarItemService.getTotalAmountByItemEntities(calendarItemList);

            calendarResponseDto.setTotalAmount(totalAmountDto.getTotalAmount());
            allCalendarDtoList.add( calendarResponseDto );
        }

        // 달력 날짜 오름차순으로 정렬
        allCalendarDtoList.sort(Comparator.comparing(CalendarResponseDto::getDate));

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
