package com.myretail.casestudy.exception;

public class JsonFieldNotFoundException extends RuntimeException {
  private static final long serialVersionUID = 5358828512223291658L;

  public JsonFieldNotFoundException(String message) {
    super(message);
  }

  public JsonFieldNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
