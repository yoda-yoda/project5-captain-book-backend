package com.captainbook.calendaritem.dto;

import com.captainbook.calendaritem.domain.CalendarItem;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class CalendarItemResponseDto {

    private Long id;
    private String itemTitle;
    private Long itemAmount;
    private String itemType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @Builder
    public CalendarItemResponseDto(Long id, String itemTitle, Long itemAmount,
                                   LocalDateTime createdAt,  LocalDateTime updatedAt, String itemType) {
        this.id = id;
        this.itemTitle = itemTitle;
        this.itemAmount = itemAmount;
        this.itemType = itemType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public static CalendarItemResponseDto fromEntity(CalendarItem calendarItem){
        return CalendarItemResponseDto.builder()
                .id(calendarItem.getId())
                .itemTitle(calendarItem.getItemTitle())
                .itemAmount(calendarItem.getItemAmount())
                .itemType( calendarItem.getItemType().getType())
                .createdAt(calendarItem.getCreatedAt())
                .updatedAt(calendarItem.getUpdatedAt())
                .build();
    }



}
