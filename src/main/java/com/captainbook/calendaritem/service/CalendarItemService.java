package com.captainbook.calendaritem.service;

import com.captainbook.calendaritem.domain.CalendarItem;
import com.captainbook.calendaritem.dto.CalendarItemRegisterDto;
import com.captainbook.calendaritem.dto.CalendarItemResponseDto;
import com.captainbook.calendaritem.dto.CalendarItemTotalAmountDto;
import com.captainbook.calendaritem.dto.CalendarItemUpdateDto;
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
