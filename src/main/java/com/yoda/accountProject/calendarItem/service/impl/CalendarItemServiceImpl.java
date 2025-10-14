package com.yoda.accountProject.calendarItem.service.impl;

import com.yoda.accountProject.calendar.domain.Calendar;
import com.yoda.accountProject.calendar.repository.CalendarRepository;
import com.yoda.accountProject.calendarItem.domain.CalendarItem;
import com.yoda.accountProject.calendarItem.dto.CalendarItemRegisterDto;
import com.yoda.accountProject.calendarItem.dto.CalendarItemResponseDto;
import com.yoda.accountProject.calendarItem.dto.CalendarItemTotalAmountDto;
import com.yoda.accountProject.calendarItem.dto.CalendarItemUpdateDto;
import com.yoda.accountProject.calendarItem.repository.CalendarItemRepository;
import com.yoda.accountProject.calendarItem.service.CalendarItemService;
import com.yoda.accountProject.itemType.domain.ItemType;
import com.yoda.accountProject.itemType.service.ItemTypeService;
import com.yoda.accountProject.member.domain.Member;
import com.yoda.accountProject.member.service.MemberService;
import com.yoda.accountProject.system.exception.ExceptionMessage;
import com.yoda.accountProject.system.exception.calendar.CalendarNotFoundException;
import com.yoda.accountProject.system.exception.calendarItem.CalendarItemNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CalendarItemServiceImpl implements CalendarItemService {

    private final CalendarItemRepository calendarItemRepository;
    private final CalendarRepository calendarRepository;
    private final MemberService memberService;
    private final ItemTypeService itemTypeService;


    // 기존 메서드라 처리 안해도됨
    public List<CalendarItemResponseDto> getAllCalendarItem(Long calendarId) {

        Calendar calendarEntityById = calendarRepository.findById(calendarId)
                .orElseThrow( () -> new CalendarNotFoundException(ExceptionMessage.Calendar.CALENDAR_NOT_FOUND_ERROR));

        List<CalendarItem> calendarItemList = calendarEntityById.getCalendarItemList();

        List<CalendarItemResponseDto> calendarItemResponseDtoList = new ArrayList<>();

        if ( !calendarItemList.isEmpty() ) {
            for (CalendarItem calendarItem : calendarItemList) {
                calendarItemResponseDtoList.add(CalendarItemResponseDto.fromEntity(calendarItem));

            }

        }

        return calendarItemResponseDtoList;

    }


    // 멤버 처리 완료
    public List<CalendarItemResponseDto> getAllCalendarItemWithMemberId(Long calendarId, Long memberId) {

        Calendar calendarEntityByIdAndMemberId = calendarRepository.findByIdAndMemberId(calendarId, memberId)
                .orElseThrow( () -> new CalendarNotFoundException(ExceptionMessage.Calendar.CALENDAR_NOT_FOUND_ERROR));


        List<CalendarItem> calendarItemList = calendarEntityByIdAndMemberId.getCalendarItemList();

        List<CalendarItemResponseDto> calendarItemResponseDtoList = new ArrayList<>();

        if ( !calendarItemList.isEmpty() ) {
            for (CalendarItem calendarItem : calendarItemList) {
                calendarItemResponseDtoList.add(CalendarItemResponseDto.fromEntity(calendarItem));
            }
        }

        return calendarItemResponseDtoList;

    }


    // 기존 메서드라 처리 안해도됨
    public CalendarItemResponseDto getCalendarItemDto(Long calendarItemId) {


        CalendarItem entity = calendarItemRepository.findById(calendarItemId)
                .orElseThrow(() -> new CalendarItemNotFoundException(ExceptionMessage.CalendarItem.CALENDAR_ITEM_NOT_FOUND_ERROR));

        return CalendarItemResponseDto.fromEntity(entity);

    }


    // 멤버 처리 완료
    public CalendarItemResponseDto getCalendarItemDtoWithMemberId(Long calendarItemId, Long memberId) {


        CalendarItem entity = calendarItemRepository.findByIdAndMemberId(calendarItemId, memberId)
                .orElseThrow(() -> new CalendarItemNotFoundException(ExceptionMessage.CalendarItem.CALENDAR_ITEM_NOT_FOUND_ERROR));

        return CalendarItemResponseDto.fromEntity(entity);

    }




    // 멤버 처리 완료
    public CalendarItemResponseDto getCalendarItemDtoWithMember(Long calendarItemId, Long memberId) {


        CalendarItem entity = calendarItemRepository.findByIdAndMemberId(calendarItemId, memberId)
                .orElseThrow(() -> new CalendarItemNotFoundException(ExceptionMessage.CalendarItem.CALENDAR_ITEM_NOT_FOUND_ERROR));

        return CalendarItemResponseDto.fromEntity(entity);

    }


    // 멤버 처리 완료
    @Transactional
    public CalendarItemResponseDto saveItem(CalendarItemRegisterDto calendarItemRequestDto, Long calendarId, Long memberId, byte typeId) {

        Calendar calendarEntity = calendarRepository.findByIdAndMemberId(calendarId, memberId)
                .orElseThrow(() -> new CalendarNotFoundException(ExceptionMessage.Calendar.CALENDAR_NOT_FOUND_ERROR));

        Member memberEntity = memberService.getMemberEntityById(memberId);

        ItemType itemTypeEntity = itemTypeService.getItemTypeEntityById( (long) typeId );

        CalendarItem entity = CalendarItemRegisterDto.toEntity(calendarItemRequestDto, calendarEntity, memberEntity, itemTypeEntity);

        CalendarItem savedEntity = calendarItemRepository.save(entity);

        return CalendarItemResponseDto.fromEntity(savedEntity);
    }


    // 멤버 처리 완료
    @Transactional
    public void updateItem(Long calendarItemId ,CalendarItemUpdateDto calendarItemUpdateDto, Long memberId) {

        CalendarItem entity = calendarItemRepository.findByIdAndMemberId(calendarItemId, memberId)
                .orElseThrow(() -> new CalendarItemNotFoundException(ExceptionMessage.CalendarItem.CALENDAR_ITEM_NOT_FOUND_ERROR));

        byte typeId = calendarItemUpdateDto.getType().getTypeId();

        ItemType updatedItemType = itemTypeService.getItemTypeEntityById((long) typeId);

        entity.updateFromDto(calendarItemUpdateDto, updatedItemType);
    }


    // 멤버 처리 완료
    @Transactional
    public void deleteCalendarItem(Long calendarItemId, Long memberId) {

        // find로 찾고 나서 삭제를 하는 경우, 정상 작동일때 쿼리가 2번 나가므로
        // 성능을 위해(DB 쿼리 1번을 위해) 삭제 절차는 공통으로 진입하며 만약 삭제 갯수가 0이면 데이터가 존재하지 않는것이므로 오류를 던진다.
        long deletedCount = calendarItemRepository.deleteByIdAndMemberId(calendarItemId, memberId);
        if (deletedCount == 0 ) {
            throw new CalendarItemNotFoundException(ExceptionMessage.CalendarItem.CALENDAR_ITEM_NOT_FOUND_ERROR);
        }
    }

    // 나온 값에 쓰는거라 멤버처리 안해도됨
    public CalendarItemTotalAmountDto getTotalAmount(List<CalendarItemResponseDto> calendarItemResponseDtoListInCalendar) {

        Long totalMinus = 0L;
        Long totalPlus = 0L;

        CalendarItemTotalAmountDto calendarItemTotalAmountDto = new CalendarItemTotalAmountDto();

        if( calendarItemResponseDtoListInCalendar.isEmpty() ){
            return calendarItemTotalAmountDto;
        }

        for (CalendarItemResponseDto calendarItemResponseDto : calendarItemResponseDtoListInCalendar) {


            // 지출개념인지 수입개념인지 알아내기 위해 ITEM_TYPE 테이블에서 정보를 조회. 현재 명세상 id 1번이 지출(-), 2번이 수입(+) 개념이다.
            if( calendarItemResponseDto.getItemType()
                    .equals(itemTypeService.getItemTypeEntityById(1L).getType()) ) {
                totalMinus += calendarItemResponseDto.getItemAmount() ;

            } else if ( calendarItemResponseDto.getItemType()
                    .equals(itemTypeService.getItemTypeEntityById(2L).getType())  ) {
                totalPlus += calendarItemResponseDto.getItemAmount() ;
            }

        }

        calendarItemTotalAmountDto.setTotalMinus(totalMinus);
        calendarItemTotalAmountDto.setTotalPlus(totalPlus);
        calendarItemTotalAmountDto.calculateTotal();

        return calendarItemTotalAmountDto;
    }

    // 나온 값에 쓰는거라 멤버처리 안해도됨
    public CalendarItemTotalAmountDto getTotalAmountByItemEntities(List<CalendarItem> calendarItemList) {

        Long totalMinus = 0L;
        Long totalPlus = 0L;

        CalendarItemTotalAmountDto calendarItemTotalAmountDto = new CalendarItemTotalAmountDto();

        if( calendarItemList.isEmpty() ){
            return calendarItemTotalAmountDto;
        }



        for (CalendarItem calendarItem : calendarItemList) {

            // 지출개념인지 수입개념인지 알아내기 위해 ITEM_TYPE 테이블에서 정보를 조회.
            // 현재 명세상 id 1번이 지출(-), 2번이 수입(+) 개념이다.
            if( calendarItem.getItemType().getId() == 1L ) {
                totalMinus += calendarItem.getItemAmount() ;

            } else if ( calendarItem.getItemType().getId() == 2L ) {
                totalPlus += calendarItem.getItemAmount() ;
            }

        }

        calendarItemTotalAmountDto.setTotalMinus(totalMinus);
        calendarItemTotalAmountDto.setTotalPlus(totalPlus);
        calendarItemTotalAmountDto.calculateTotal();

        return calendarItemTotalAmountDto;
    }


}
