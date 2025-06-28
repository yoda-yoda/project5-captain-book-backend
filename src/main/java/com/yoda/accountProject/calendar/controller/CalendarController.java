package com.yoda.accountProject.calendar.controller;


import com.yoda.accountProject.calendar.dto.CalendarRequestDto;
import com.yoda.accountProject.calendar.dto.CalendarResponseDto;
import com.yoda.accountProject.calendar.dto.CalendarUpdateDto;
import com.yoda.accountProject.calendar.service.CalendarService;
import com.yoda.accountProject.calendarItem.service.CalendarItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping("/home")
    public String home(Model model){

        List<CalendarResponseDto> calendarResponseDtoList = calendarService.getAllCalendar();

        model.addAttribute("calendarResponseDtoList", calendarResponseDtoList);

        return "home";
    }


    @PostMapping("/home")
    public String homePost(@ModelAttribute CalendarRequestDto calendarRequestDto){

        CalendarResponseDto calendarResponseDto = calendarService.saveCalendar(calendarRequestDto);

        return "redirect:/home";
    }




    @GetMapping("/calendar/update/{calendarId}")
    public String calendarUpdate(@PathVariable Long calendarId, Model model){

        CalendarResponseDto dto = calendarService.getCalendarDtoById(calendarId);

        model.addAttribute("calendar", dto);

        return "calendar-update";
    }



    @PostMapping("/calendar/update/{calendarId}")
    public String calendarUpdate(
            @PathVariable Long calendarId,
            @ModelAttribute CalendarUpdateDto calendarUpdateDto){

        calendarService.updateCalendar(calendarId, calendarUpdateDto);

        return "redirect:/home";
    }



    @GetMapping("/create")
    public String create(){
        return "create";
    }


    @PostMapping("/calendar/delete/{calendarResponseDtoId}")
    public String calendarDelete(@PathVariable Long calendarResponseDtoId){

        calendarService.deleteCalendar(calendarResponseDtoId);

        return "redirect:/home";
        
    }



}
