package com.yoda.accountProject.system.exception.calendarItem;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CalendarItemNotFoundException extends RuntimeException {
    public CalendarItemNotFoundException(String message) {
        super(message);
    }

    public CalendarItemNotFoundException() {
        super();
    }

    public CalendarItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CalendarItemNotFoundException(Throwable cause) {
        super(cause);
    }


}
