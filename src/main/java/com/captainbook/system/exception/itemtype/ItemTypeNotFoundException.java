package com.captainbook.system.exception.itemtype;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemTypeNotFoundException extends RuntimeException {

    public ItemTypeNotFoundException(String message) {
        super(message);
    }

    public ItemTypeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ItemTypeNotFoundException(Throwable cause) {
        super(cause);
    }

    public ItemTypeNotFoundException() {
    }
}
