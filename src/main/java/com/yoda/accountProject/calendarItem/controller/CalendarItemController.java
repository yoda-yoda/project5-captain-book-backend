package com.yoda.accountProject.calendarItem.controller;

import com.yoda.accountProject.calendar.dto.CalendarResponseDto;
import com.yoda.accountProject.calendar.service.CalendarService;
import com.yoda.accountProject.calendarItem.dto.CalendarItemRegisterDto;
import com.yoda.accountProject.calendarItem.dto.CalendarItemResponseDto;
import com.yoda.accountProject.calendarItem.dto.CalendarItemUpdateDto;
import com.yoda.accountProject.calendarItem.service.CalendarItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CalendarItemController {

    private final CalendarService calendarService;
    private final CalendarItemService calendarItemService;


    @GetMapping("/calendar/{calendarId}/item")
    public String itemHome(@PathVariable Long calendarId, Model model){

        List<CalendarItemResponseDto> calendarItemResponseDtoList = calendarItemService.getAllCalendarItem(calendarId);

        CalendarResponseDto calendarResponseDto = calendarService.getCalendarDtoById(calendarId);


        model.addAttribute("calendarResponseDto", calendarResponseDto);

        model.addAttribute("calendarItemResponseDtoList", calendarItemResponseDtoList);

        return "item";
    }


    @PostMapping("/calendar/{calendarId}/item")
    public String itemCreate(@PathVariable Long calendarId,
                             @ModelAttribute CalendarItemRegisterDto calendarItemRequestDto
                             ){

        byte typeId = calendarItemRequestDto.getType().getTypeId();
        calendarItemService.saveItem(calendarItemRequestDto, calendarId, typeId);


        return "redirect:/calendar/" + calendarId + "/item";
    }



    @GetMapping("/calendar/{calendarId}/item/{calendarItemId}/update")
    public String calendarItemUpdate(@PathVariable Long calendarItemId, @PathVariable Long calendarId, Model model){

        CalendarItemResponseDto calendarItemDto = calendarItemService.getCalendarItemDto(calendarItemId);

        model.addAttribute("calendarItemDto", calendarItemDto);
        model.addAttribute("calendarId", calendarId);


        return "item-update";
    }


    @PostMapping("/calendar/{calendarId}/item/{calendarItemId}/update")
    public String calendarItemUpdate(@PathVariable Long calendarItemId,
                                     @PathVariable Long calendarId,
                                     @ModelAttribute CalendarItemUpdateDto calendarItemUpdateDto){

        calendarItemService.updateItem(calendarItemId, calendarItemUpdateDto);

        return "redirect:/calendar/" + calendarId + "/item";
    }


    @PostMapping("/calendar/{calendarId}/item/{calendarItemId}/delete")
    public String calendarItemDelete(@PathVariable Long calendarItemId, @PathVariable Long calendarId){

        calendarItemService.deleteCalendarItem(calendarItemId);

        return "redirect:/calendar/" + calendarId + "/item";
    }


    @GetMapping("/calendar/{calendarId}/item/create")
    public String create(@PathVariable Long calendarId, Model model){

        model.addAttribute("calendarId", calendarId);

        return "item-create";
    }



}
