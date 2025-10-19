package com.captainbook.calendaritem.domain;

import com.captainbook.calendar.domain.Calendar;
import com.captainbook.calendaritem.dto.CalendarItemUpdateDto;
import com.captainbook.itemtype.domain.ItemType;
import com.captainbook.member.domain.Member;
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
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_type_id", nullable = false)
    private ItemType itemType;

    @Column(nullable = false, length = 45)
    private String itemTitle;

    // 현재 schema.sql 파일로 999_999_999_999 이하의 DB 컬럼 제약을 걸어놓은 상태이다.
    @Column(nullable = false)
    private Long itemAmount;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public CalendarItem(Calendar calendar, Member member, String itemTitle, Long itemAmount, ItemType itemType) {
        this.calendar = calendar;
        this.member = member;
        this.itemTitle = itemTitle;
        this.itemAmount = itemAmount;
        this.itemType = itemType;
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
