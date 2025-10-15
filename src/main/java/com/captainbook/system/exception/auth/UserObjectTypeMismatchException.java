package com.captainbook.system.exception.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserObjectTypeMismatchException extends RuntimeException {

  public UserObjectTypeMismatchException(String message) {
        super(message);
    }

  public UserObjectTypeMismatchException(String message, Throwable cause) {
    super(message, cause);
  }

  public UserObjectTypeMismatchException(Throwable cause) {
    super(cause);
  }

  public UserObjectTypeMismatchException() {
  }
}
