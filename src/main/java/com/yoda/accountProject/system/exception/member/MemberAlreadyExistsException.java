package com.yoda.accountProject.system.exception.member;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MemberAlreadyExistsException extends RuntimeException {
    public MemberAlreadyExistsException(String message) {
        super(message);
    }

    public MemberAlreadyExistsException() {
        super();
    }

    public MemberAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    protected MemberAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
