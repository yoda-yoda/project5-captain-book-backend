package com.yoda.accountProject.calendar.controller;

import com.yoda.accountProject.auth.service.AuthService;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CalendarController {


    private final CalendarService calendarService;
    private final AuthService authService;


    // 멤버 처리 완료, 없음처리는 리액트에서 완료
    @GetMapping("/home")
    public ResponseEntity<ResponseData<CalendarFinalResponseDto>> allCalendarRead
            (HttpServletRequest request, @AuthenticationPrincipal OAuth2User oauth2User) {


        Long currentMemberId = authService.getOAuthCurrentMemberId(oauth2User);


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


    // 멤버 처리 완료, 없음처리 서비스에서 완료
    @GetMapping("/calendar/{calendarId}")
    public ResponseEntity<ResponseData<CalendarResponseDto>> calendarRead(@PathVariable Long calendarId,
                                                                          @AuthenticationPrincipal OAuth2User oauth2User) {


        Long currentMemberId = authService.getOAuthCurrentMemberId(oauth2User);
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


    // 멤버처리 완료, 없음처리 필요없음
    @PostMapping("/home")
    public ResponseEntity<ResponseData<CalendarResponseDto>> calendarCreate
            (@RequestBody @Valid CalendarRequestDto calendarRequestDto,
            @AuthenticationPrincipal OAuth2User oauth2User ){


        Long currentMemberId = authService.getOAuthCurrentMemberId(oauth2User);

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



    // 멤버 처리 완료, 없음처리 서비스에서 완료
    @PutMapping("/calendar/update/{calendarId}")
    public ResponseEntity<ResponseData<Void>> calendarUpdate(
            @PathVariable Long calendarId,
            @RequestBody @Valid CalendarUpdateDto calendarUpdateDto,
            @AuthenticationPrincipal OAuth2User oauth2User ){


        Long currentMemberId = authService.getOAuthCurrentMemberId(oauth2User);

        calendarService.updateCalendar(calendarId, calendarUpdateDto, currentMemberId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseData.<Void>builder()
                                .statusCode(HttpStatus.OK.value())
                                .build()
                );
    }



    // 멤버 처리완료, 없음 처리 서비스에서 완료
    @DeleteMapping("/calendar/delete/{calendarResponseDtoId}")
    public ResponseEntity<ResponseData<Void>> calendarDelete(
            @PathVariable Long calendarResponseDtoId,
            @AuthenticationPrincipal OAuth2User oauth2User){


        Long currentMemberId = authService.getOAuthCurrentMemberId(oauth2User);


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
