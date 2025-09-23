package com.yoda.accountProject.calendarItem.dto;

import com.yoda.accountProject.calendar.domain.Calendar;
import com.yoda.accountProject.calendarItem.domain.CalendarItem;
import com.yoda.accountProject.calendarItem.domain.Type;
import com.yoda.accountProject.itemType.domain.ItemType;
import com.yoda.accountProject.member.domain.Member;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CalendarItemRegisterDto {

    @NotNull
    @Size(max = 45, message = "항목명은 45자 이하여야 합니다.")
    private String itemTitle;

    @NotNull
    @Max(value = 999_999_999_999L, message = "허용 가능 금액을 초과하였습니다.")
    private Long itemAmount;

    @NotNull
    private Type type;

    @Builder
    public CalendarItemRegisterDto(String itemTitle, Long itemAmount, Type type) {
        this.itemTitle = itemTitle;
        this.itemAmount = itemAmount;
        this.type = type;
    }

    public static CalendarItem toEntity(CalendarItemRegisterDto calendarItemRequestDto, Calendar calendar, Member memberEntity, ItemType itemType){

        return CalendarItem.builder()
                .itemTitle(calendarItemRequestDto.getItemTitle())
                .itemAmount(calendarItemRequestDto.getItemAmount())
                .calendar(calendar)
                .member(memberEntity)
                .itemType(itemType)
                .build();
    }


}
