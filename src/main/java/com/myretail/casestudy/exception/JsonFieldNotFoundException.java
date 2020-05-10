package com.myretail.casestudy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class JsonFieldNotFoundException extends RuntimeException {
  private static final long serialVersionUID = 5358828512223291658L;

  public JsonFieldNotFoundException(String message) {
    super(message);
  }

  public JsonFieldNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
