package com.yoda.accountProject.calendarItem.controller;

import com.yoda.accountProject.auth.service.AuthService;
import com.yoda.accountProject.calendar.dto.CalendarResponseDto;
import com.yoda.accountProject.calendarItem.dto.*;
import com.yoda.accountProject.calendarItem.service.CalendarItemService;
import com.yoda.accountProject.calendar.service.CalendarService;
import com.yoda.accountProject.system.common.response.ResponseData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CalendarItemController {

    private final CalendarService calendarService;
    private final CalendarItemService calendarItemService;
    private final AuthService authService;

    // 멤버 처리 완료, 없음처리는 리액트에서 완료
    @GetMapping("/calendar/{calendarId}/item")
    public ResponseEntity<ResponseData<CalendarItemFinalResponseDto>> allCalendarItemRead(
            @PathVariable Long calendarId, @AuthenticationPrincipal Object principal){

        Long currentMemberId = authService.getCurrentMemberId(principal);

        List<CalendarItemResponseDto> calendarItemResponseDtoList = calendarItemService.getAllCalendarItemWithMemberId(calendarId, currentMemberId);
        CalendarItemTotalAmountDto totalAmountDto = calendarItemService.getTotalAmount(calendarItemResponseDtoList);
        CalendarResponseDto calendarResponseDto = calendarService.getCalendarDtoByIdAndMemberId(calendarId, currentMemberId);


        CalendarItemFinalResponseDto res = new CalendarItemFinalResponseDto(calendarResponseDto, calendarItemResponseDtoList, totalAmountDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseData.<CalendarItemFinalResponseDto>builder()
                                .statusCode(HttpStatus.OK.value())
                                .data(res)
                                .build()
                );
    }


    // 멤버 처리 완료
    @GetMapping("/calendar/{calendarId}/item/{calendarItemId}")
    public ResponseEntity<ResponseData<CalendarItemResponseDto>> calendarItemRead(
            @PathVariable Long calendarItemId,
            @PathVariable Long calendarId,
            @AuthenticationPrincipal Object principal){

        Long currentMemberId = authService.getCurrentMemberId(principal);

        // 먼저 해당 달력이 존재하는지 확인
        calendarService.getCalendarDtoByIdAndMemberId(calendarId, currentMemberId);

        CalendarItemResponseDto res = calendarItemService.getCalendarItemDtoWithMemberId(calendarItemId, currentMemberId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseData.<CalendarItemResponseDto>builder()
                                .statusCode(HttpStatus.OK.value())
                                .data(res)
                                .build()
                );
    }


    // 멤버 처리 완료
    @PostMapping("/calendar/{calendarId}/item")
    public ResponseEntity<ResponseData<CalendarItemResponseDto>> calendarItemCreate(
            @PathVariable Long calendarId,
            @AuthenticationPrincipal Object principal,
            @RequestBody @Valid CalendarItemRegisterDto calendarItemRequestDto){

        Long currentMemberId = authService.getCurrentMemberId(principal);

        // 공식 명세상의 typeId에 따른 item Type을 확인하기 위함이다. 현재 명세상 typeId == 1이 EXPENSE(지출) 이다.
        byte typeId = calendarItemRequestDto.getType().getTypeId();

        CalendarItemResponseDto res = calendarItemService.saveItem(calendarItemRequestDto, calendarId, currentMemberId, typeId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ResponseData.<CalendarItemResponseDto>builder()
                                .statusCode(HttpStatus.CREATED.value())
                                .data(res)
                                .build()
                );
    }



    // 멤버 처리 완료
    @PutMapping("/calendar/item/{calendarItemId}/update")
    public ResponseEntity<ResponseData<Void>> calendarItemUpdate(@PathVariable Long calendarItemId,
                                     @RequestBody @Valid CalendarItemUpdateDto calendarItemUpdateDto,
                                     @AuthenticationPrincipal Object principal){

        Long currentMemberId = authService.getCurrentMemberId(principal);

        calendarItemService.updateItem(calendarItemId, calendarItemUpdateDto, currentMemberId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseData.<Void>builder()
                                .statusCode(HttpStatus.OK.value())
                                .build()
                );
    }


    // 멤버 처리 완료
    @DeleteMapping("/calendar/item/{calendarItemId}/delete")
    public ResponseEntity<ResponseData<Void>> calendarItemDelete(
            @PathVariable Long calendarItemId,
            @AuthenticationPrincipal Object principal){

        Long currentMemberId = authService.getCurrentMemberId(principal);

        calendarItemService.deleteCalendarItem(calendarItemId, currentMemberId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(
                        ResponseData.<Void>builder()
                                .statusCode(HttpStatus.NO_CONTENT.value())
                                .build()
                );
    }



}
