package com.yoda.accountProject.system.exception.calendarItem;

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
