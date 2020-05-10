package com.myretail.casestudy.exception;

public class ProductNotFoundException extends RuntimeException {
  private static final long serialVersionUID = 4358828512223291658L;

  public ProductNotFoundException(String message) {
    super(message);
  }

  public ProductNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
