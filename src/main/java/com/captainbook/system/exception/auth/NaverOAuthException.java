package com.captainbook.system.exception.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NaverOAuthException extends RuntimeException {

  public NaverOAuthException(String message) {
        super(message);
    }

  public NaverOAuthException(String message, Throwable cause) {
    super(message, cause);
  }

  public NaverOAuthException(Throwable cause) {
    super(cause);
  }

  public NaverOAuthException() {
  }
}
