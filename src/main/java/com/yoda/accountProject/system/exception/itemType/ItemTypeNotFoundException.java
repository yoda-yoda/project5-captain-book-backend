package com.yoda.accountProject.system.exception.itemType;

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
