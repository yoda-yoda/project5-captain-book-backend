package com.yoda.accountProject.calendarItem.service;

import com.yoda.accountProject.calendarItem.domain.CalendarItem;
import com.yoda.accountProject.calendarItem.dto.CalendarItemRegisterDto;
import com.yoda.accountProject.calendarItem.dto.CalendarItemResponseDto;
import com.yoda.accountProject.calendarItem.dto.CalendarItemTotalAmountDto;
import com.yoda.accountProject.calendarItem.dto.CalendarItemUpdateDto;
import java.util.List;


public interface CalendarItemService {

    List<CalendarItemResponseDto> getAllCalendarItem(Long calendarId);

    CalendarItemResponseDto getCalendarItemDto(Long calendarItemId);

    CalendarItemResponseDto saveItem(CalendarItemRegisterDto calendarItemRequestDto, Long calendarId, byte typeId);

    void updateItem(Long calendarItemId , CalendarItemUpdateDto calendarItemUpdateDto);

    void deleteCalendarItem(Long calendarItemId);

    CalendarItemTotalAmountDto getTotalAmount(List<CalendarItemResponseDto> calendarItemResponseDtoListInCalendar);

    CalendarItemTotalAmountDto getTotalAmountByItemEntities(List<CalendarItem> calendarItemList);
}
