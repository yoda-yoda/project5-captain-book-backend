package com.captainbook.system.exception.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class KakaoOAuthException extends RuntimeException {

  public KakaoOAuthException(String message) {
        super(message);
    }

  public KakaoOAuthException(String message, Throwable cause) {
    super(message, cause);
  }

  public KakaoOAuthException(Throwable cause) {
    super(cause);
  }

  public KakaoOAuthException() {
  }
}
