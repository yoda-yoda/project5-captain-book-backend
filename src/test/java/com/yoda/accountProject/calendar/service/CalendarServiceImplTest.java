package com.yoda.accountProject.calendar.service;


import com.yoda.accountProject.calendar.domain.Calendar;
import com.yoda.accountProject.calendar.dto.CalendarRequestDto;
import com.yoda.accountProject.calendar.dto.CalendarResponseDto;
import com.yoda.accountProject.calendar.repository.CalendarRepository;
import com.yoda.accountProject.calendar.service.CalendarServiceImpl;
import com.yoda.accountProject.system.exception.ExceptionMessage;
import com.yoda.accountProject.system.exception.calendar.CalendarNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Slf4j
class CalendarServiceImplTest {

    @Autowired
    private CalendarRepository calendarRepository;
    @Autowired
    private CalendarServiceImpl calendarServiceImpl;



    @Test
    @DisplayName("saveCalendar 메서드 테스트1 - Calendar request Dto 를 받아 엔티티에 맵핑하고 저장 후 response Dto를 반환하는 메서드다.")
    void saveCalendar_method_test1() throws Exception {

        // given

        CalendarRequestDto reqDto = CalendarRequestDto.builder()
                .date("2025-01-01")
                .title("테스트 제목")
                .build();

        //when

        CalendarResponseDto resDto = calendarServiceImpl.saveCalendar(reqDto);

        Calendar entity = calendarRepository.findById(resDto.getId())
                .orElseThrow(() -> new CalendarNotFoundException(ExceptionMessage.Calendar.CALENDAR_NOT_FOUND_ERROR));

        //then
        assertThat(entity.getId()).isEqualTo(resDto.getId());

        log.info("entity.getId() = {}", entity.getId());
        log.info("resDto.getId() = {}", resDto.getId());


    }



}