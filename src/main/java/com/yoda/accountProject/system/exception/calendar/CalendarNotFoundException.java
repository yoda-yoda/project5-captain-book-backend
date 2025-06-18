package com.yoda.accountProject.system.exception.calendar;

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
