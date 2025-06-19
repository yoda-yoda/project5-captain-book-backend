package com.yoda.accountProject.calendar.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class CalendarUpdateDto {

    private String title;

    private String date;

    @Builder
    public CalendarUpdateDto(String title, String date) {
        this.title = title;
        this.date = date;
    }

}
