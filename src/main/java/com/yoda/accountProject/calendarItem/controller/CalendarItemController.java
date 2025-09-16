package com.yoda.accountProject.calendarItem.controller;

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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CalendarItemController {

    private final CalendarService calendarService;
    private final CalendarItemService calendarItemService;

    @GetMapping("/calendar/{calendarId}/item")
    public ResponseEntity<ResponseData<CalendarItemFinalResponseDto>> allCalendarItemRead(@PathVariable Long calendarId){

        List<CalendarItemResponseDto> calendarItemResponseDtoList = calendarItemService.getAllCalendarItem(calendarId);
        CalendarItemTotalAmountDto totalAmountDto = calendarItemService.getTotalAmount(calendarItemResponseDtoList);
        CalendarResponseDto calendarResponseDto = calendarService.getCalendarDtoById(calendarId);


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


    @GetMapping("/calendar/{calendarId}/item/{calendarItemId}")
    public ResponseEntity<ResponseData<CalendarItemResponseDto>> calendarItemRead(@PathVariable Long calendarItemId){

        CalendarItemResponseDto res = calendarItemService.getCalendarItemDto(calendarItemId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseData.<CalendarItemResponseDto>builder()
                                .statusCode(HttpStatus.OK.value())
                                .data(res)
                                .build()
                );
    }


    @PostMapping("/calendar/{calendarId}/item")
    public ResponseEntity<ResponseData<CalendarItemResponseDto>> calendarItemCreate(@PathVariable Long calendarId,
                             @RequestBody @Valid CalendarItemRegisterDto calendarItemRequestDto
                             ){

        // 공식 명세상의 typeId에 따른 item Type을 확인하기 위함이다. 현재 명세상 typeId == 1이 EXPENSE(지출) 이다.
        byte typeId = calendarItemRequestDto.getType().getTypeId();

        CalendarItemResponseDto res = calendarItemService.saveItem(calendarItemRequestDto, calendarId, typeId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ResponseData.<CalendarItemResponseDto>builder()
                                .statusCode(HttpStatus.CREATED.value())
                                .data(res)
                                .build()
                );
    }



    @PutMapping("/calendar/{calendarId}/item/{calendarItemId}/update")
    public ResponseEntity<ResponseData<Void>> calendarItemUpdate(@PathVariable Long calendarItemId,
                                     @RequestBody @Valid CalendarItemUpdateDto calendarItemUpdateDto){

        calendarItemService.updateItem(calendarItemId, calendarItemUpdateDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseData.<Void>builder()
                                .statusCode(HttpStatus.OK.value())
                                .build()
                );
    }


    @DeleteMapping("/calendar/{calendarId}/item/{calendarItemId}/delete")
    public ResponseEntity<ResponseData<Void>> calendarItemDelete(@PathVariable Long calendarItemId){

        calendarItemService.deleteCalendarItem(calendarItemId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(
                        ResponseData.<Void>builder()
                                .statusCode(HttpStatus.NO_CONTENT.value())
                                .build()
                );
    }



}
