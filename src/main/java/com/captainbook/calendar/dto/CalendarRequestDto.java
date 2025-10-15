package com.captainbook.calendar.dto;

import com.captainbook.calendar.domain.Calendar;
import com.captainbook.member.domain.Member;
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
public class CalendarRequestDto {

    @NotNull
    @Size(max = 35, message = "달력명은 35자 이하여야 합니다.")
    private String title;

    @NotNull
    private LocalDate date;

    @AssertTrue(message = "달력 날짜는 9999-12-31 이하여야 합니다.")
    public boolean isValidDate() {
        return date == null || !date.isAfter(LocalDate.of(9999, 12, 31));
    }

    public static Calendar toEntity(CalendarRequestDto calendarRequestDto, Member memberEntity){
        return Calendar.builder()
                .title(calendarRequestDto.getTitle())
                .date(calendarRequestDto.getDate())
                .member(memberEntity)
                .build();
    }


    @Builder
    public CalendarRequestDto(String title, LocalDate date) {
        this.title = title;
        this.date = date;
    }
}
