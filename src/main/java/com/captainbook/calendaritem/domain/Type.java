package com.captainbook.calendaritem.domain;

import lombok.Getter;

@Getter
public enum Type {

    // 이때 클라이언트 html 라디오버튼의 name과 객체명이 같아아, 컨트롤러에서도 잘 매핑될것이다
    EXPENSE(1),
    INCOME(2);

    private final byte typeId;

    Type(int typeId) {
        this.typeId = (byte) typeId;
    }


}
