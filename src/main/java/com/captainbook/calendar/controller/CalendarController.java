package com.captainbook.calendar.controller;

import com.captainbook.auth.service.AuthService;
import com.captainbook.calendar.dto.CalendarFinalResponseDto;
import com.captainbook.calendar.dto.CalendarRequestDto;
import com.captainbook.calendar.dto.CalendarResponseDto;
import com.captainbook.calendar.dto.CalendarUpdateDto;
import com.captainbook.calendar.service.CalendarService;
import com.captainbook.system.common.response.ResponseData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CalendarController {


    private final CalendarService calendarService;
    private final AuthService authService;


    @GetMapping("/home")
    public ResponseEntity<ResponseData<CalendarFinalResponseDto>> allCalendarRead
            (HttpServletRequest request, @AuthenticationPrincipal Object principal) {


        Long currentMemberId = authService.getCurrentMemberId(principal);


        List<CalendarResponseDto> calendarResponseDtoList = calendarService.getAllCalendar(currentMemberId);
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
    public ResponseEntity<ResponseData<CalendarResponseDto>> calendarRead(@PathVariable Long calendarId,
                                                                          @AuthenticationPrincipal Object principal) {


        Long currentMemberId = authService.getCurrentMemberId(principal);
        CalendarResponseDto dto = calendarService.getCalendarDtoByIdAndMemberId(calendarId, currentMemberId);


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
            (@RequestBody @Valid CalendarRequestDto calendarRequestDto,
             @AuthenticationPrincipal Object principal ){


        Long currentMemberId = authService.getCurrentMemberId(principal);

        CalendarResponseDto res = calendarService.saveCalendar(calendarRequestDto, currentMemberId);

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
            @RequestBody @Valid CalendarUpdateDto calendarUpdateDto,
            @AuthenticationPrincipal Object principal ){


        Long currentMemberId = authService.getCurrentMemberId(principal);

        calendarService.updateCalendar(calendarId, calendarUpdateDto, currentMemberId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseData.<Void>builder()
                                .statusCode(HttpStatus.OK.value())
                                .build()
                );
    }


    @DeleteMapping("/calendar/delete/{calendarResponseDtoId}")
    public ResponseEntity<ResponseData<Void>> calendarDelete(
            @PathVariable Long calendarResponseDtoId,
            @AuthenticationPrincipal Object principal){


        Long currentMemberId = authService.getCurrentMemberId(principal);

        calendarService.deleteCalendar(calendarResponseDtoId, currentMemberId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(
                        ResponseData.<Void>builder()
                                .statusCode(HttpStatus.NO_CONTENT.value())
                                .build()
                );
    }



}
