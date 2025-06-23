package com.yoda.accountProject.calendarItem.domain;

import lombok.Getter;

@Getter
public enum Type {

    // 이때 html 라디오버튼의 name과 객체명이 같아아, 컨트롤러의 모델어트리뷰트에서도 잘 매핑됨
    EXPENSE(1),
    INCOME(2);

    private final byte typeId;

    Type(int typeId) {
        this.typeId = (byte) typeId;
    }


}
