package com.yoda.accountProject.calendarItem.service.impl;

import com.yoda.accountProject.calendar.domain.Calendar;
import com.yoda.accountProject.calendar.service.CalendarService;
import com.yoda.accountProject.calendarItem.domain.CalendarItem;
import com.yoda.accountProject.calendarItem.dto.CalendarItemRegisterDto;
import com.yoda.accountProject.calendarItem.dto.CalendarItemResponseDto;
import com.yoda.accountProject.calendarItem.dto.CalendarItemTotalAmountDto;
import com.yoda.accountProject.calendarItem.dto.CalendarItemUpdateDto;
import com.yoda.accountProject.calendarItem.repository.CalendarItemRepository;
import com.yoda.accountProject.calendarItem.service.CalendarItemService;
import com.yoda.accountProject.itemType.domain.ItemType;
import com.yoda.accountProject.itemType.service.ItemTypeService;
import com.yoda.accountProject.system.exception.ExceptionMessage;
import com.yoda.accountProject.system.exception.calendarItem.CalendarItemNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CalendarItemServiceImpl implements CalendarItemService {

    private final CalendarItemRepository calendarItemRepository;
    private final CalendarService calendarService;
    private final ItemTypeService itemTypeService;


    public List<CalendarItemResponseDto> getAllCalendarItem(Long calendarId) {

        Calendar calendarEntityById = calendarService.getCalendarEntityById(calendarId);

        List<CalendarItem> calendarItemList = calendarEntityById.getCalendarItemList();

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

        CalendarItemResponseDto calendarItemResponseDto = CalendarItemResponseDto.fromEntity(entity);

        return calendarItemResponseDto;

    }



    public CalendarItemResponseDto saveItem(CalendarItemRegisterDto calendarItemRequestDto, Long calendarId, byte typeId) {

        Calendar calendarEntity = calendarService.getCalendarEntityById(calendarId);
        ItemType itemTypeEntity = itemTypeService.getItemTypeEntityById( (long) typeId );

        CalendarItem entity = CalendarItemRegisterDto.toEntity(calendarItemRequestDto, calendarEntity, itemTypeEntity);
        CalendarItem savedEntity = calendarItemRepository.save(entity);

        return CalendarItemResponseDto.fromEntity(savedEntity);
    }


    @Transactional
    public void updateItem(Long calendarItemId ,CalendarItemUpdateDto calendarItemUpdateDto) {

        CalendarItem entity = calendarItemRepository.findById(calendarItemId)
                .orElseThrow(() -> new CalendarItemNotFoundException(ExceptionMessage.CalendarItem.CALENDAR_ITEM_NOT_FOUND_ERROR));

        byte typeId = calendarItemUpdateDto.getType().getTypeId();

        ItemType updatedItemType = itemTypeService.getItemTypeEntityById((long) typeId);

        entity.updateFromDto(calendarItemUpdateDto, updatedItemType);
    }


    public void deleteCalendarItem(Long calendarItemId) {

        calendarItemRepository.deleteById(calendarItemId);

    }


    public CalendarItemTotalAmountDto getTotalAmount(List<CalendarItemResponseDto> calendarItemResponseDtoList) {

        Long totalMinus = 0L;
        Long totalPlus = 0L;

        CalendarItemTotalAmountDto calendarItemTotalAmountDto = new CalendarItemTotalAmountDto();

        if( calendarItemResponseDtoList.isEmpty() ){
            return calendarItemTotalAmountDto;
        }

        for (CalendarItemResponseDto calendarItemResponseDto : calendarItemResponseDtoList) {


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
        calendarItemTotalAmountDto.sum();

        return calendarItemTotalAmountDto;
    }


}
