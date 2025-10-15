package com.captainbook.calendaritem.service.impl;

import com.captainbook.calendar.domain.Calendar;
import com.captainbook.calendar.repository.CalendarRepository;
import com.captainbook.calendaritem.domain.CalendarItem;
import com.captainbook.calendaritem.dto.CalendarItemRegisterDto;
import com.captainbook.calendaritem.dto.CalendarItemResponseDto;
import com.captainbook.calendaritem.dto.CalendarItemTotalAmountDto;
import com.captainbook.calendaritem.dto.CalendarItemUpdateDto;
import com.captainbook.calendaritem.repository.CalendarItemRepository;
import com.captainbook.calendaritem.service.CalendarItemService;
import com.captainbook.itemtype.domain.ItemType;
import com.captainbook.itemtype.service.ItemTypeService;
import com.captainbook.member.domain.Member;
import com.captainbook.member.service.MemberService;
import com.captainbook.system.exception.ExceptionMessage;
import com.captainbook.system.exception.calendar.CalendarNotFoundException;
import com.captainbook.system.exception.calendaritem.CalendarItemNotFoundException;
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


    public CalendarItemResponseDto getCalendarItemDto(Long calendarItemId) {


        CalendarItem entity = calendarItemRepository.findById(calendarItemId)
                .orElseThrow(() -> new CalendarItemNotFoundException(ExceptionMessage.CalendarItem.CALENDAR_ITEM_NOT_FOUND_ERROR));

        return CalendarItemResponseDto.fromEntity(entity);

    }


    public CalendarItemResponseDto getCalendarItemDtoWithMemberId(Long calendarItemId, Long memberId) {


        CalendarItem entity = calendarItemRepository.findByIdAndMemberId(calendarItemId, memberId)
                .orElseThrow(() -> new CalendarItemNotFoundException(ExceptionMessage.CalendarItem.CALENDAR_ITEM_NOT_FOUND_ERROR));

        return CalendarItemResponseDto.fromEntity(entity);

    }



    public CalendarItemResponseDto getCalendarItemDtoWithMember(Long calendarItemId, Long memberId) {


        CalendarItem entity = calendarItemRepository.findByIdAndMemberId(calendarItemId, memberId)
                .orElseThrow(() -> new CalendarItemNotFoundException(ExceptionMessage.CalendarItem.CALENDAR_ITEM_NOT_FOUND_ERROR));

        return CalendarItemResponseDto.fromEntity(entity);

    }


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


    @Transactional
    public void updateItem(Long calendarItemId ,CalendarItemUpdateDto calendarItemUpdateDto, Long memberId) {

        CalendarItem entity = calendarItemRepository.findByIdAndMemberId(calendarItemId, memberId)
                .orElseThrow(() -> new CalendarItemNotFoundException(ExceptionMessage.CalendarItem.CALENDAR_ITEM_NOT_FOUND_ERROR));

        byte typeId = calendarItemUpdateDto.getType().getTypeId();

        ItemType updatedItemType = itemTypeService.getItemTypeEntityById((long) typeId);

        entity.updateFromDto(calendarItemUpdateDto, updatedItemType);
    }


    @Transactional
    public void deleteCalendarItem(Long calendarItemId, Long memberId) {

        // find로 찾고 나서 삭제를 하는 경우, 정상 작동일때 쿼리가 2번 나가므로
        // 성능을 위해(DB 쿼리 1번을 위해) 삭제 절차는 공통으로 진입하며 만약 삭제 갯수가 0이면 데이터가 존재하지 않는것이므로 오류를 던진다.
        long deletedCount = calendarItemRepository.deleteByIdAndMemberId(calendarItemId, memberId);
        if (deletedCount == 0 ) {
            throw new CalendarItemNotFoundException(ExceptionMessage.CalendarItem.CALENDAR_ITEM_NOT_FOUND_ERROR);
        }
    }

    public CalendarItemTotalAmountDto getTotalAmount(List<CalendarItemResponseDto> calendarItemResponseDtoListInCalendar) {

        Long totalMinus = 0L;
        Long totalPlus = 0L;

        CalendarItemTotalAmountDto calendarItemTotalAmountDto = new CalendarItemTotalAmountDto();

        if( calendarItemResponseDtoListInCalendar.isEmpty() ){
            return calendarItemTotalAmountDto;
        }

        for (CalendarItemResponseDto calendarItemResponseDto : calendarItemResponseDtoListInCalendar) {


            // '지출개념'인지 '수입개념'인지 알아내기 위해 ITEM_TYPE 테이블에서 정보를 조회한다.
            // 현재 명세상으로는 id 1번이 지출(-), 2번이 수입(+) 개념이다.
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


    public CalendarItemTotalAmountDto getTotalAmountByItemEntities(List<CalendarItem> calendarItemList) {

        Long totalMinus = 0L;
        Long totalPlus = 0L;

        CalendarItemTotalAmountDto calendarItemTotalAmountDto = new CalendarItemTotalAmountDto();

        if( calendarItemList.isEmpty() ){
            return calendarItemTotalAmountDto;
        }



        for (CalendarItem calendarItem : calendarItemList) {

            // '지출개념'인지 '수입개념'인지 알아내기 위해 ITEM_TYPE 테이블에서 정보를 조회한다.
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
