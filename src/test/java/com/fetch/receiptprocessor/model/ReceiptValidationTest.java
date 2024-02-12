package com.fetch.receiptprocessor.model;

import com.fetch.receiptprocessor.service.ReceiptServiceTest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ReceiptValidationTest {

  private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
  private final Validator validator = factory.getValidator();

  @Test
  public void testRetailerNotNull() {

    Receipt receipt = new ReceiptServiceTest.ReceiptBuilder()
            .purchaseDate(LocalDate.now())
            .purchaseTime(LocalTime.parse("13:45"))
            .total("10.00")
            .items(Collections.singletonList(new Item("Test Item", "5.00", 0)))
            .build();
    Set<ConstraintViolation<Receipt>> violations = validator.validate(receipt);
    assertFalse(violations.isEmpty());
    assertEquals("retailer is required", violations.iterator().next().getMessage());
  }

  @Test
  public void testRetailerPattern() {
    Receipt receipt = new ReceiptServiceTest.ReceiptBuilder()
            .retailer("Invalid Retailer Name!")
            .purchaseDate(LocalDate.now())
            .purchaseTime(LocalTime.now())
            .total("10.00")
            .items(Collections.singletonList(new Item("Test Item", "5.00", 0)))
            .build();
    Set<ConstraintViolation<Receipt>> violations = validator.validate(receipt);
    assertFalse(violations.isEmpty());
  }

  @Test
  public void testRetailerPattern3() {
    Receipt receipt = new ReceiptServiceTest.ReceiptBuilder()
            .retailer("Invalid Retailer Name&")
            .purchaseDate(LocalDate.now())
            .purchaseTime(LocalTime.now())
            .total("10.00")
            .items(Collections.singletonList(new Item("Test Item", "5.00", 0)))
            .build();
    Set<ConstraintViolation<Receipt>> violations = validator.validate(receipt);
    assertFalse(violations.isEmpty());
  }

  @Test
  public void testRetailerNotNull2() {
    Receipt receipt = new ReceiptServiceTest.ReceiptBuilder()
            .purchaseDate(LocalDate.now())
            .purchaseTime(LocalTime.now())
            .total("10.00")
            .items(Collections.singletonList(new Item("Test Item", "5.00", 0)))
            .build();
    Set<ConstraintViolation<Receipt>> violations = validator.validateProperty(receipt, "retailer");
    assertFalse(violations.isEmpty());
    assertEquals("retailer is required", violations.iterator().next().getMessage());
  }

  @Test
  public void testRetailerPattern2() {
    Receipt receipt = new ReceiptServiceTest.ReceiptBuilder()
            .retailer("Invalid Retailer Name%^&*")
            .purchaseDate(LocalDate.now())
            .purchaseTime(LocalTime.now())
            .total("10.00")
            .items(Collections.singletonList(new Item("Test Item", "5.00", 0)))
            .build();
    Set<ConstraintViolation<Receipt>> violations = validator.validateProperty(receipt, "retailer");
    assertFalse(violations.isEmpty());
    assertEquals("retailer format is invalid", violations.iterator().next().getMessage());
  }

  @Test
  public void testPurchaseDateNotNull() {
    Receipt receipt = new ReceiptServiceTest.ReceiptBuilder()
            .retailer("Valid Retailer")
            .purchaseTime(LocalTime.now())
            .total("10.00")
            .items(Collections.singletonList(new Item("Test Item", "5.00", 0)))
            .build();
    Set<ConstraintViolation<Receipt>> violations = validator.validateProperty(receipt, "purchaseDate");
    assertFalse(violations.isEmpty());
    assertEquals("purchaseDate must be in the format yyyy-MM-dd", violations.iterator().next().getMessage());
  }

  @Test
  public void testPurchaseTimeNotNull() {
    Receipt receipt = new ReceiptServiceTest.ReceiptBuilder()
            .retailer("Valid Retailer")
            .purchaseDate(LocalDate.now())
            .total("10.00")
            .items(Collections.singletonList(new Item("Test Item", "5.00", 0)))
            .build();
    Set<ConstraintViolation<Receipt>> violations = validator.validateProperty(receipt, "purchaseTime");
    assertFalse(violations.isEmpty());
    assertEquals("purchaseTime is required", violations.iterator().next().getMessage());
  }

  @Test
  public void testTotalNotNull() {
    Receipt receipt = new ReceiptServiceTest.ReceiptBuilder()
            .retailer("Valid Retailer")
            .purchaseDate(LocalDate.now())
            .purchaseTime(LocalTime.now())
            .items(Collections.singletonList(new Item("Test Item", "5.00", 0)))
            .build();
    Set<ConstraintViolation<Receipt>> violations = validator.validateProperty(receipt, "total");
    assertFalse(violations.isEmpty());
    assertEquals("total is required", violations.iterator().next().getMessage());
  }

  @Test
  public void testTotalPattern() {
    Receipt receipt = new ReceiptServiceTest.ReceiptBuilder()
            .retailer("Valid Retailer")
            .purchaseDate(LocalDate.now())
            .purchaseTime(LocalTime.now())
            .total("InvalidTotal") // Invalid format
            .items(Collections.singletonList(new Item("Test Item", "5.00", 0)))
            .build();
    Set<ConstraintViolation<Receipt>> violations = validator.validateProperty(receipt, "total");
    assertFalse(violations.isEmpty());
    assertEquals("total format is invalid", violations.iterator().next().getMessage());
  }

  @Test
  public void testItemsNotNull() {
    Receipt receipt = new ReceiptServiceTest.ReceiptBuilder()
            .retailer("Valid Retailer")
            .purchaseDate(LocalDate.now())
            .purchaseTime(LocalTime.now())
            .total("10.00")
            .build();
    Set<ConstraintViolation<Receipt>> violations = validator.validateProperty(receipt, "items");
    assertFalse(violations.isEmpty());
    assertEquals("Items are required", violations.iterator().next().getMessage());
  }

  @Test
  public void testItemsSizeMin() {
    Receipt receipt = new ReceiptServiceTest.ReceiptBuilder()
            .retailer("Valid Retailer")
            .purchaseDate(LocalDate.now())
            .purchaseTime(LocalTime.now())
            .total("10.00")
            .items(Collections.emptyList()) // No items provided
            .build();
    Set<ConstraintViolation<Receipt>> violations = validator.validateProperty(receipt, "items");
    assertFalse(violations.isEmpty());
    assertEquals("There must be at least one item in items", violations.iterator().next().getMessage());
  }

  @Test
  public void testValidPurchaseDateAndTime() {

    Receipt receipt = new ReceiptServiceTest.ReceiptBuilder()
            .retailer("Invalid Retailer Name&")
            .purchaseDate(LocalDate.of(2022, 1, 1)) // Valid date
            .purchaseTime(LocalTime.of(13, 1)) // Valid time
            .total("10.00")
            .items(Collections.singletonList(new Item("Test Item", "5.00", 0)))
            .build();
    Set<ConstraintViolation<Receipt>> violations = validator.validate(receipt);
    assertTrue(violations.isEmpty(), "Expected no violations");
  }
}

