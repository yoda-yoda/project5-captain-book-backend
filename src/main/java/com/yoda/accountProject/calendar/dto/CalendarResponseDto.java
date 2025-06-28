package com.yoda.accountProject.calendar.dto;

import com.yoda.accountProject.calendar.domain.Calendar;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalendarResponseDto {

    private Long id;
    private String date;
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
    public CalendarResponseDto(Long id, String date, String title) {
        this.id = id;
        this.date = date;
        this.title = title;
    }
}
