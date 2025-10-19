package com.captainbook.calendar.service.impl;

import com.captainbook.calendar.domain.Calendar;
import com.captainbook.calendar.dto.CalendarRequestDto;
import com.captainbook.calendar.dto.CalendarResponseDto;
import com.captainbook.calendar.dto.CalendarUpdateDto;
import com.captainbook.calendar.repository.CalendarRepository;
import com.captainbook.calendar.service.CalendarService;
import com.captainbook.calendaritem.domain.CalendarItem;
import com.captainbook.calendaritem.dto.CalendarItemTotalAmountDto;
import com.captainbook.calendaritem.service.CalendarItemService;
import com.captainbook.member.domain.Member;
import com.captainbook.member.service.MemberService;
import com.captainbook.system.exception.ExceptionMessage;
import com.captainbook.system.exception.calendar.CalendarNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CalendarServiceImpl implements CalendarService {

    private final CalendarRepository calendarRepository;
    private final CalendarItemService calendarItemService;
    private final MemberService memberService;


    @Transactional
    public CalendarResponseDto saveCalendar(CalendarRequestDto calendarRequestDto, Long currentMemberId) {

        Member memberEntity = memberService.getMemberEntityById(currentMemberId);

        Calendar entity = CalendarRequestDto.toEntity(calendarRequestDto, memberEntity);

        Calendar savedEntity = calendarRepository.save(entity);

        return CalendarResponseDto.fromEntity(savedEntity);
    }


    public List<CalendarResponseDto> getAllCalendar(Long currentMemberId) {

        List<CalendarResponseDto> allCalendarDtoList = new ArrayList<>();


        List<Calendar> calendarList = calendarRepository.findByMemberId(currentMemberId);

        // 데이터가 없다면 빈 리스트 반환
        if(calendarList.isEmpty()){
            return allCalendarDtoList;
        }

        // 나중에 이안에서 getCalendarItemList 할때 조인페치 쿼리를 작성하는것이 좋을것같다.
        // 왜냐하면 한번의 쿼리로 필요한 데이터를 객체에 모두 할당하기위함이다.
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

        // 달력 날짜를 오름차순으로 정렬
        allCalendarDtoList.sort(Comparator.comparing(CalendarResponseDto::getDate));

        return allCalendarDtoList;

    }


    public Long getTotalCalendarAmountSum(List<CalendarResponseDto> allCalendarDtoList) {

        Long sum = 0L;

        if( allCalendarDtoList.isEmpty() ){
            return sum;
        }

        for (CalendarResponseDto calendarResponseDto : allCalendarDtoList) {
            sum += calendarResponseDto.getTotalAmount();
        }

        return sum;
    }


    public CalendarResponseDto getCalendarDtoByIdAndMemberId(Long calendarId, Long currentMemberId) {

        Calendar findEntity = calendarRepository.findByIdAndMemberId(calendarId, currentMemberId)
                .orElseThrow( () -> new CalendarNotFoundException(ExceptionMessage.Calendar.CALENDAR_NOT_FOUND_ERROR));

        return CalendarResponseDto.fromEntity(findEntity);
    }


    public Calendar getCalendarEntityById(Long calendarId){
        return calendarRepository.findById(calendarId)
                .orElseThrow( () -> new CalendarNotFoundException(ExceptionMessage.Calendar.CALENDAR_NOT_FOUND_ERROR));
    }


    @Transactional
    public void updateCalendar(Long calendarId, CalendarUpdateDto calendarUpdateDto, Long currentMemberId) {

        Calendar calendar = calendarRepository.findByIdAndMemberId(calendarId, currentMemberId)
                .orElseThrow(() -> new CalendarNotFoundException(ExceptionMessage.Calendar.CALENDAR_NOT_FOUND_ERROR));

        calendar.updateFromDto(calendarUpdateDto);
    }


    @Transactional
    public void deleteCalendar(Long calendarId, Long currentMemberId) {

        // find로 찾고 나서 삭제를 하는 경우, 정상 작동일때 쿼리가 2번 나가므로
        // 성능을 위해(DB 쿼리 1번을 위해) 삭제 절차는 공통으로 진입하며 만약 삭제 갯수가 0이면 데이터가 존재하지 않는것이므로 오류를 던진다.
        long deletedCount = calendarRepository.deleteByIdAndMemberId(calendarId, currentMemberId);
        if (deletedCount == 0) {
            throw new CalendarNotFoundException(ExceptionMessage.Calendar.CALENDAR_NOT_FOUND_ERROR);
        }

    }



}
