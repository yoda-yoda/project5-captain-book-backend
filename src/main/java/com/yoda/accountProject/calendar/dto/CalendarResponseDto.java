package com.yoda.accountProject.calendar.dto;

import com.yoda.accountProject.calendar.domain.Calendar;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CalendarResponseDto {

    private Long id;
    private LocalDate date;
    private String title;
    private Long totalAmount = 0L;

    public static CalendarResponseDto fromEntity(Calendar calendar){
        return CalendarResponseDto.builder()
                .id(calendar.getId())
                .date(calendar.getDate())
                .title(calendar.getTitle())
                .build();
    }

    @Builder
    public CalendarResponseDto(Long id, LocalDate date, String title) {
        this.id = id;
        this.date = date;
        this.title = title;
    }
}
