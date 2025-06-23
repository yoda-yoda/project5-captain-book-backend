package com.yoda.accountProject.calendarItem.domain;

import com.yoda.accountProject.calendar.domain.Calendar;
import com.yoda.accountProject.calendarItem.dto.CalendarItemUpdateDto;
import com.yoda.accountProject.itemType.domain.ItemType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class CalendarItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_id", nullable = false)
    private Calendar calendar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_type_id", nullable = false)
    private ItemType itemType;

    private String itemTitle;
    private Long itemAmount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public CalendarItem(Calendar calendar, String itemTitle, Long itemAmount, ItemType itemType) {
        this.calendar = calendar;
        this.itemTitle = itemTitle;
        this.itemType = itemType;
        this.itemAmount = itemAmount;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateFromDto(CalendarItemUpdateDto calendarItemUpdateDto, ItemType updatedItemType){
        this.itemType = updatedItemType;
        this.itemTitle = calendarItemUpdateDto.getItemTitle();
        this.itemAmount = calendarItemUpdateDto.getItemAmount();
        this.updatedAt = LocalDateTime.now();
    }


}
