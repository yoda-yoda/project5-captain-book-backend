package com.yoda.accountProject.calendarItem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalendarItemTotalAmountDto {

    private Long totalPlus = 0L;
    private Long totalMinus = 0L;
    private Long totalAmount = 0L;

    public void calculateTotal(){
        this.totalAmount = this.totalPlus - this.totalMinus;
    }


}
