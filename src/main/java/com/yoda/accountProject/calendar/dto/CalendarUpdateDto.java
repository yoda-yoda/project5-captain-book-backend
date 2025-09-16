package com.yoda.accountProject.calendar.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
public class CalendarUpdateDto {

    @NotNull
    @Size(max = 35, message = "달력명은 35자 이하여야 합니다.")
    private String title;

    @NotNull
    private LocalDate date;

    @AssertTrue(message = "달력 날짜는 9999-12-31 이하여야 합니다.")
    public boolean isValidDate() {
        return date == null || !date.isAfter(LocalDate.of(9999, 12, 31));
    }

    @Builder
    public CalendarUpdateDto(String title, LocalDate date) {
        this.title = title;
        this.date = date;
    }

}
