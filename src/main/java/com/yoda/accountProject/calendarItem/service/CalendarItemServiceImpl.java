package com.yoda.accountProject.calendarItem.service;

import com.yoda.accountProject.calendar.domain.Calendar;
import com.yoda.accountProject.calendar.service.impl.CalendarServiceImpl;
import com.yoda.accountProject.calendarItem.domain.CalendarItem;
import com.yoda.accountProject.calendarItem.dto.CalendarItemRegisterDto;
import com.yoda.accountProject.calendarItem.dto.CalendarItemResponseDto;
import com.yoda.accountProject.calendarItem.dto.CalendarItemUpdateDto;
import com.yoda.accountProject.calendarItem.repository.CalendarItemRepository;
import com.yoda.accountProject.itemType.domain.ItemType;
import com.yoda.accountProject.itemType.service.ItemTypeServiceImpl;
import com.yoda.accountProject.system.exception.ExceptionMessage;
import com.yoda.accountProject.system.exception.calendarItem.CalendarItemNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CalendarItemServiceImpl {

    private final CalendarItemRepository calendarItemRepository;
    private final CalendarServiceImpl calendarService;
    private final ItemTypeServiceImpl itemTypeService;

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


}
