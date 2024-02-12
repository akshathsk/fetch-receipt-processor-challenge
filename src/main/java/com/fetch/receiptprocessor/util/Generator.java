package com.fetch.receiptprocessor.util;

import java.util.UUID;

public class Generator {

  public static String generateReceiptId() {

    return UUID.randomUUID().toString();
  }
}
