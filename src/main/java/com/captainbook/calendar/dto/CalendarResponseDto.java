package com.captainbook.calendar.dto;

import com.captainbook.calendar.domain.Calendar;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class CalendarResponseDto {

    private Long id;
    private LocalDate date;
    private String title;
    private Long totalAmount = 0L;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CalendarResponseDto fromEntity(Calendar calendar){
        return CalendarResponseDto.builder()
                .id(calendar.getId())
                .date(calendar.getDate())
                .title(calendar.getTitle())
                .createdAt(calendar.getCreatedAt())
                .updatedAt(calendar.getUpdatedAt())
                .build();
    }

    @Builder
    public CalendarResponseDto(Long id, LocalDate date, String title, LocalDateTime createdAt,  LocalDateTime updatedAt) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
