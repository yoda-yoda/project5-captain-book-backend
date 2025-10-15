package com.captainbook.system.common.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


// 공통된 응답 메시지 포맷을 위해 만든 래퍼 객체이다

@Getter
@Setter
public class ResponseData <T> {
    private int statusCode;
    private T data;

    @Builder
    public ResponseData(int statusCode, T data) {
        this.statusCode = statusCode;
        this.data = data;
    }
}
