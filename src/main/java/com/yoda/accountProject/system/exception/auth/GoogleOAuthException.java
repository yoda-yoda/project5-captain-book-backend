package com.yoda.accountProject.system.exception.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GoogleOAuthException extends RuntimeException {

  public GoogleOAuthException(String message) {
        super(message);
    }

  public GoogleOAuthException(String message, Throwable cause) {
    super(message, cause);
  }

  public GoogleOAuthException(Throwable cause) {
    super(cause);
  }

  public GoogleOAuthException() {
  }
}
