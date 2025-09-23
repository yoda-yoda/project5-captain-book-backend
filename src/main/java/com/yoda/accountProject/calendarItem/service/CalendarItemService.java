package com.yoda.accountProject.calendarItem.service;

import com.yoda.accountProject.calendarItem.domain.CalendarItem;
import com.yoda.accountProject.calendarItem.dto.CalendarItemRegisterDto;
import com.yoda.accountProject.calendarItem.dto.CalendarItemResponseDto;
import com.yoda.accountProject.calendarItem.dto.CalendarItemTotalAmountDto;
import com.yoda.accountProject.calendarItem.dto.CalendarItemUpdateDto;
import java.util.List;


public interface CalendarItemService {

    List<CalendarItemResponseDto> getAllCalendarItem(Long calendarId);

    List<CalendarItemResponseDto> getAllCalendarItemWithMemberId(Long calendarId, Long memberId);

    CalendarItemResponseDto getCalendarItemDto(Long calendarItemId);

    CalendarItemResponseDto getCalendarItemDtoWithMemberId(Long calendarItemId, Long memberId);

    CalendarItemResponseDto saveItem(CalendarItemRegisterDto calendarItemRequestDto, Long calendarId, Long memberId, byte typeId);

    void updateItem(Long calendarItemId , CalendarItemUpdateDto calendarItemUpdateDto, Long memberId);

    void deleteCalendarItem(Long calendarItemId, Long memberId);

    CalendarItemTotalAmountDto getTotalAmount(List<CalendarItemResponseDto> calendarItemResponseDtoListInCalendar);

    CalendarItemTotalAmountDto getTotalAmountByItemEntities(List<CalendarItem> calendarItemList);
}
