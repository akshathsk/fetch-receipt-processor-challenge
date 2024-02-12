package com.fetch.receiptprocessor.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ItemTest {

  private static Validator validator;

  @BeforeAll
  static void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void testValidItem() {
    Item item = new Item("Milk 1L", "3.99", 0);
    Set<ConstraintViolation<Item>> violations = validator.validate(item);
    assertTrue(violations.isEmpty());
  }

  @Test
  void testInvalidShortDescription() {

    Item item = new Item("Invalid!@#", "3.99", 0);
    Set<ConstraintViolation<Item>> violations = validator.validate(item);
    assertEquals(1, violations.size());
    ConstraintViolation<Item> violation = violations.iterator().next();
    assertEquals("shortDescription format is invalid", violation.getMessage());
  }

  @Test
  void testInvalidPrice() {

    Item item = new Item("Milk 1L", "invalid_price", 0);
    Set<ConstraintViolation<Item>> violations = validator.validate(item);
    assertEquals(1, violations.size());
    ConstraintViolation<Item> violation = violations.iterator().next();
    assertEquals("price format is invalid", violation.getMessage());
  }

  @Test
  void testNullShortDescription() {

    Item item = new Item(null, "3.99", 0);
    Set<ConstraintViolation<Item>> violations = validator.validate(item);
    assertEquals(1, violations.size());
    ConstraintViolation<Item> violation = violations.iterator().next();
    assertEquals("shortDescription is required", violation.getMessage());
  }

  @Test
  void testNullPrice() {

    Item item = new Item("Milk 1L", null, 0);
    Set<ConstraintViolation<Item>> violations = validator.validate(item);
    assertEquals(1, violations.size());
    ConstraintViolation<Item> violation = violations.iterator().next();
    assertEquals("price is required", violation.getMessage());
  }
}
