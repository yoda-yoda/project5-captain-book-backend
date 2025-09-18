package com.yoda.accountProject.calendar.controller;

import com.yoda.accountProject.calendar.dto.CalendarFinalResponseDto;
import com.yoda.accountProject.calendar.dto.CalendarRequestDto;
import com.yoda.accountProject.calendar.dto.CalendarResponseDto;
import com.yoda.accountProject.calendar.dto.CalendarUpdateDto;
import com.yoda.accountProject.calendar.service.CalendarService;
import com.yoda.accountProject.system.common.response.ResponseData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping("/home")
    public ResponseEntity<ResponseData<CalendarFinalResponseDto>> allCalendarRead(HttpServletRequest request) {

        String origin = request.getHeader("Origin");
        System.out.println("Origin =====>" + origin);


        List<CalendarResponseDto> calendarResponseDtoList = calendarService.getAllCalendar();
        Long calendarTotalSum = calendarService.getTotalCalendarAmountSum(calendarResponseDtoList);

        CalendarFinalResponseDto res = new CalendarFinalResponseDto(calendarResponseDtoList, calendarTotalSum);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseData.<CalendarFinalResponseDto>builder()
                        .statusCode(HttpStatus.OK.value())
                        .data(res)
                        .build()
                );
    }



    @GetMapping("/calendar/{calendarId}")
    public ResponseEntity<ResponseData<CalendarResponseDto>> calendarRead(@PathVariable Long calendarId) {

        CalendarResponseDto dto = calendarService.getCalendarDtoById(calendarId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseData.<CalendarResponseDto>builder()
                                .statusCode(HttpStatus.OK.value())
                                .data(dto)
                                .build()
                );
    }



    @PostMapping("/home")
    public ResponseEntity<ResponseData<CalendarResponseDto>> calendarCreate
            (@RequestBody @Valid CalendarRequestDto calendarRequestDto){

        CalendarResponseDto res = calendarService.saveCalendar(calendarRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ResponseData.<CalendarResponseDto>builder()
                                .statusCode(HttpStatus.CREATED.value())
                                .data(res)
                                .build()
                );
    }



    @PutMapping("/calendar/update/{calendarId}")
    public ResponseEntity<ResponseData<Void>> calendarUpdate(
            @PathVariable Long calendarId,
            @RequestBody @Valid CalendarUpdateDto calendarUpdateDto){

        calendarService.updateCalendar(calendarId, calendarUpdateDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseData.<Void>builder()
                                .statusCode(HttpStatus.OK.value())
                                .build()
                );
    }



    @DeleteMapping("/calendar/delete/{calendarResponseDtoId}")
    public ResponseEntity<ResponseData<Void>> calendarDelete(@PathVariable Long calendarResponseDtoId){

        calendarService.deleteCalendar(calendarResponseDtoId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(
                        ResponseData.<Void>builder()
                                .statusCode(HttpStatus.NO_CONTENT.value())
                                .build()
                );
    }



}
