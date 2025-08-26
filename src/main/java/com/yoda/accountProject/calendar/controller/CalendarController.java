package com.yoda.accountProject.calendar.controller;

import com.yoda.accountProject.calendar.dto.CalendarFinalResponseDto;
import com.yoda.accountProject.calendar.dto.CalendarRequestDto;
import com.yoda.accountProject.calendar.dto.CalendarResponseDto;
import com.yoda.accountProject.calendar.dto.CalendarUpdateDto;
import com.yoda.accountProject.calendar.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping("/home")
    public CalendarFinalResponseDto allCalendarRead() {

        List<CalendarResponseDto> calendarResponseDtoList = calendarService.getAllCalendar();
        Long calendarTotalSum = calendarService.getTotalCalendarAmountSum(calendarResponseDtoList);

        return new CalendarFinalResponseDto(calendarResponseDtoList, calendarTotalSum);
    }


    @PostMapping("/home")
    public CalendarResponseDto calendarCreate(@RequestBody CalendarRequestDto calendarRequestDto){

        return calendarService.saveCalendar(calendarRequestDto);
    }




//    @GetMapping("/calendar/update/{calendarId}")
//    public String calendarUpdate(@PathVariable Long calendarId, Model model){
//
//        CalendarResponseDto dto = calendarService.getCalendarDtoById(calendarId);
//
//        model.addAttribute("calendar", dto);
//
//        return "calendar-update";
//    }



    @PutMapping("/calendar/update/{calendarId}")
    public String calendarUpdate(
            @PathVariable Long calendarId,
            @RequestBody CalendarUpdateDto calendarUpdateDto){

        calendarService.updateCalendar(calendarId, calendarUpdateDto);

        return "";
    }



//    @GetMapping("/create")
//    public String create(){
//        return "calendar-create";
//    }


    @DeleteMapping("/calendar/delete/{calendarResponseDtoId}")
    public String calendarDelete(@PathVariable Long calendarResponseDtoId){

        calendarService.deleteCalendar(calendarResponseDtoId);

        return "";
    }



}
