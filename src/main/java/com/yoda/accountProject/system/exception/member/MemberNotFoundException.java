package com.yoda.accountProject.system.exception.member;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class MemberNotFoundException extends RuntimeException {

  public MemberNotFoundException(String message) {
        super(message);
    }

  public MemberNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public MemberNotFoundException(Throwable cause) {
    super(cause);
  }

  public MemberNotFoundException() {
  }
}
