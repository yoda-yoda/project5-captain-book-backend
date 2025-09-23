package com.yoda.accountProject.calendar.service.impl;

import com.yoda.accountProject.calendar.domain.Calendar;
import com.yoda.accountProject.calendar.dto.CalendarRequestDto;
import com.yoda.accountProject.calendar.dto.CalendarResponseDto;
import com.yoda.accountProject.calendar.dto.CalendarUpdateDto;
import com.yoda.accountProject.calendar.repository.CalendarRepository;
import com.yoda.accountProject.calendar.service.CalendarService;
import com.yoda.accountProject.calendarItem.domain.CalendarItem;
import com.yoda.accountProject.calendarItem.dto.CalendarItemTotalAmountDto;
import com.yoda.accountProject.calendarItem.service.CalendarItemService;
import com.yoda.accountProject.member.domain.Member;
import com.yoda.accountProject.member.service.MemberService;
import com.yoda.accountProject.system.exception.ExceptionMessage;
import com.yoda.accountProject.system.exception.calendar.CalendarNotFoundException;
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


    // 멤버 처리 완료
    @Transactional
    public CalendarResponseDto saveCalendar(CalendarRequestDto calendarRequestDto, Long currentMemberId) {

        Member memberEntity = memberService.getMemberEntityById(currentMemberId);

        Calendar entity = CalendarRequestDto.toEntity(calendarRequestDto, memberEntity);

        Calendar savedEntity = calendarRepository.save(entity);

        return CalendarResponseDto.fromEntity(savedEntity);
    }


    // 멤버 처리 완료
    public List<CalendarResponseDto> getAllCalendar(Long currentMemberId) {

        List<CalendarResponseDto> allCalendarDtoList = new ArrayList<>();

        // 여기서 findAll을 jpql로 짜야 N+1 예방되긴할듯 아무튼
        //  List<Calendar> calendarList = calendarRepository.findAll();

        List<Calendar> calendarList = calendarRepository.findByMemberId(currentMemberId);

        // 데이터가 없다면 빈 리스트 반환
        if(calendarList.isEmpty()){
            return allCalendarDtoList;
        }

        // 나중에 이안에 getCalendarItemList 할때 조인페치 쿼리작성하기. 왜냐하면 한번의 쿼리로 필요한 데이터를 객체에 모두 할당하기위함
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


    // 처리 된거에다 쓰이니 멤버 처리 안해도됨
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


    // 멤버 처리 완료
    public CalendarResponseDto getCalendarDtoByIdAndMemberId(Long calendarId, Long currentMemberId) {

        Calendar findEntity = calendarRepository.findByIdAndMemberId(calendarId, currentMemberId)
                .orElseThrow( () -> new CalendarNotFoundException(ExceptionMessage.Calendar.CALENDAR_NOT_FOUND_ERROR));

        return CalendarResponseDto.fromEntity(findEntity);
    }


    // 기존 메서드라 안해도됨
    public Calendar getCalendarEntityById(Long calendarId){
        return calendarRepository.findById(calendarId)
                .orElseThrow( () -> new CalendarNotFoundException(ExceptionMessage.Calendar.CALENDAR_NOT_FOUND_ERROR));
    }


    // 멤버 처리 완료
    @Transactional
    public void updateCalendar(Long calendarId, CalendarUpdateDto calendarUpdateDto, Long currentMemberId) {

        Calendar calendar = calendarRepository.findByIdAndMemberId(calendarId, currentMemberId)
                .orElseThrow(() -> new CalendarNotFoundException(ExceptionMessage.Calendar.CALENDAR_NOT_FOUND_ERROR));

        calendar.updateFromDto(calendarUpdateDto);
    }


    // 멤버 처리 완료
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
