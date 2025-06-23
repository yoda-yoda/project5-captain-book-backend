package com.yoda.accountProject.calendarItem.service;

import com.yoda.accountProject.calendar.domain.Calendar;
import com.yoda.accountProject.calendar.dto.CalendarRequestDto;
import com.yoda.accountProject.calendar.dto.CalendarResponseDto;
import com.yoda.accountProject.calendar.repository.CalendarRepository;
import com.yoda.accountProject.calendar.service.CalendarService;
import com.yoda.accountProject.calendarItem.domain.CalendarItem;
import com.yoda.accountProject.calendarItem.domain.Type;
import com.yoda.accountProject.calendarItem.dto.CalendarItemRegisterDto;
import com.yoda.accountProject.calendarItem.dto.CalendarItemResponseDto;
import com.yoda.accountProject.calendarItem.dto.CalendarItemUpdateDto;
import com.yoda.accountProject.calendarItem.repository.CalendarItemRepository;
import com.yoda.accountProject.system.exception.ExceptionMessage;
import com.yoda.accountProject.system.exception.calendar.CalendarNotFoundException;
import com.yoda.accountProject.system.exception.calendarItem.CalendarItemNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class CalendarItemServiceImplTest {

    @Autowired
    private CalendarRepository calendarRepository;
    @Autowired
    private CalendarService calendarService;
    @Autowired
    private CalendarItemRepository calendarItemRepository;
    @Autowired
    private CalendarItemService calendarItemService;

    // 테스트용 객체
    private Calendar testCalendar;
    private CalendarItem testCalendarItem;




    @BeforeEach
    @DisplayName("Calendar의 request Dto를 생성하여 엔티티에 맵핑하고 DB에 저장")
    void setUp1() {

        // given
        CalendarRequestDto reqDto = CalendarRequestDto.builder()
                .date("2025-01-01")
                .title("테스트1")
                .build();

        //when
        CalendarResponseDto resDto = calendarService.saveCalendar(reqDto);

        Calendar entity = calendarRepository.findById(resDto.getId())
                .orElseThrow( () -> new CalendarNotFoundException(ExceptionMessage.Calendar.CALENDAR_NOT_FOUND_ERROR));

        testCalendar = entity;

        //then
        assertThat(entity.getId()).isEqualTo(resDto.getId());

        log.info("entity.getId() = {}", entity.getId());
        log.info("resDto.getId() = {}", resDto.getId());
    }


    @BeforeEach
    @DisplayName("setUp1 메서드에서 만들어진 Calendar에 속할 CalendarItem을 DB에 저장")
    void setUp2() {

        // given
        CalendarItemRegisterDto registerDto = CalendarItemRegisterDto.builder()
                .itemAmount(5000L)
                .itemTitle("라면")
                .type(Type.EXPENSE)
                .build();

        byte typeId = registerDto.getType().getTypeId();

        //when
        CalendarItemResponseDto calendarItemResponseDto = calendarItemService.saveItem(registerDto, testCalendar.getId(), typeId);


        CalendarItem calendarItemEntity = calendarItemRepository.findById(calendarItemResponseDto.getId())
                .orElseThrow(() -> new CalendarItemNotFoundException(ExceptionMessage.CalendarItem.CALENDAR_ITEM_NOT_FOUND_ERROR));

        testCalendarItem = calendarItemEntity;

        //then
        assertThat(calendarItemEntity.getId()).isEqualTo(calendarItemResponseDto.getId());

        log.info("calendarItemEntity.getId() = {}", calendarItemEntity.getId());
        log.info("calendarItemResponseDto.getId() = {}", calendarItemResponseDto.getId());
    }


    @AfterEach
    @DisplayName("BeforeEach 메서드 이후 전부 삭제")
    void tearDown() {
        calendarItemRepository.deleteAll();
        calendarRepository.deleteAll();
    }



    @Test
    @DisplayName("CalendarItemUpdateDto 를 통해 CalendarItem 을 update 하는 메서드 테스트")
    void updateItemMethodTest() throws Exception {

        // given
        CalendarItemUpdateDto updateItemDto = CalendarItemUpdateDto.builder()
                .itemAmount(9999L)
                .itemTitle("updated itemTitle")
                .type(Type.EXPENSE)
                .build();

        //when
        calendarItemService.updateItem(testCalendarItem.getId() ,updateItemDto);

        CalendarItem updatedEntity = calendarItemRepository.findById(testCalendarItem.getId())
                .orElseThrow(() -> new CalendarItemNotFoundException(ExceptionMessage.CalendarItem.CALENDAR_ITEM_NOT_FOUND_ERROR));


        //then
        assertThat(updatedEntity.getItemTitle()).isEqualTo("updated itemTitle");

        log.info("updatedEntity.getItemTitle() = {}", updatedEntity.getItemTitle());


    }





}





