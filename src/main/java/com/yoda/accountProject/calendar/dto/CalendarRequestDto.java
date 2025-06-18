package com.yoda.accountProject.calendar.dto;

import com.yoda.accountProject.calendar.domain.Calendar;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;


@Getter
@Setter
@NoArgsConstructor
public class CalendarRequestDto {

    private String title;
    private String date;

    public static Calendar toEntity(CalendarRequestDto calendarRequestDto){

        return Calendar.builder()
                .date(calendarRequestDto.getDate())
                .title(calendarRequestDto.getTitle())
                .build();
    }

    @Builder
    public CalendarRequestDto(String title, String date) {
        this.title = title;
        this.date = date;
    }
}
