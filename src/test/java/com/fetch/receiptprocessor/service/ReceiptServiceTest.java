package com.fetch.receiptprocessor.service;

import com.fetch.receiptprocessor.model.Item;
import com.fetch.receiptprocessor.model.Receipt;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class ReceiptServiceTest {

  public static class ReceiptBuilder {
    private String retailer;
    private LocalDate date;
    private LocalTime time;
    private String total;
    private List<Item> items;

    public ReceiptBuilder retailer(String retailer) {
      this.retailer = retailer;
      return this;
    }

    public ReceiptBuilder purchaseDate(LocalDate purchaseDate) {
      this.date = purchaseDate;
      return this;
    }

    public ReceiptBuilder purchaseTime(LocalTime purchaseTime) {
      this.time = purchaseTime;
      return this;
    }

    public ReceiptBuilder total(String total) {
      this.total = total;
      return this;
    }

    public ReceiptBuilder items(List<Item> items) {
      this.items = items;
      return this;
    }

    public Receipt build() {
      return new Receipt(retailer, (date != null) ? date.toString() : "", (time != null) ? time.toString() : null, total, items, date, time, 0d);
    }
  }
}
