package com.fetch.receiptprocessor.exception;

public class AbsentException extends Exception {

  public AbsentException(String receiptId) {
    super(String.format("No receipt found for that id"));
  }
}
