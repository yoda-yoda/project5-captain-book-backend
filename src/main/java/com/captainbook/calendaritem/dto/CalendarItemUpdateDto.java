package com.captainbook.calendaritem.dto;

import com.captainbook.calendaritem.domain.Type;
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
public class CalendarItemUpdateDto {

    @NotNull
    @Size(max = 45, message = "항목명은 45자 이하여야 합니다.")
    private String itemTitle;

    @NotNull
    @Max(value = 999_999_999_999L, message = "허용 가능 금액을 초과하였습니다.")
    private Long itemAmount;

    @NotNull
    private Type type;

    @Builder
    public CalendarItemUpdateDto(String itemTitle, Long itemAmount, Type type) {
        this.itemTitle = itemTitle;
        this.itemAmount = itemAmount;
        this.type = type;
    }
}
