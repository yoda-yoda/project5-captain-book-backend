package com.yoda.accountProject.system.common.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
