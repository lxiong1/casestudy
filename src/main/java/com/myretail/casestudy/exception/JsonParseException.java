package com.myretail.casestudy.exception;

public class JsonParseException extends RuntimeException {
  private static final long serialVersionUID = 1358828512223291658L;

  public JsonParseException(String message) {
    super(message);
  }

  public JsonParseException(String message, Throwable cause) {
    super(message, cause);
  }
}
