package com.yoda.accountProject.system.exception.calendar;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CalendarNotFoundException extends RuntimeException {
    public CalendarNotFoundException(String message) {
        super(message);
    }

    public CalendarNotFoundException() {
        super();
    }

    public CalendarNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CalendarNotFoundException(Throwable cause) {
        super(cause);
    }


}
