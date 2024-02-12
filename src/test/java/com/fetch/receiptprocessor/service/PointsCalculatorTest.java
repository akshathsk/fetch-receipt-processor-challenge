package com.fetch.receiptprocessor.service;

import com.fetch.receiptprocessor.exception.ValidationException;
import com.fetch.receiptprocessor.model.Item;
import com.fetch.receiptprocessor.model.Receipt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PointsCalculatorTest {

  @Mock
  private PointsConfig pointsConfig;

  @InjectMocks
  private PointsCalculator pointsCalculator;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    when(pointsConfig.getRetailerName()).thenReturn(1);
    when(pointsConfig.getRoundDollar()).thenReturn(50);
    when(pointsConfig.getMultipleOfQuarter()).thenReturn(25);
    when(pointsConfig.getPerTwoItems()).thenReturn(5);
    when(pointsConfig.getMultipleOfThreeItemDescription()).thenReturn(0.2);
    when(pointsConfig.getOddDay()).thenReturn(6);
    when(pointsConfig.getAfternoonBonus()).thenReturn(10);
    when(pointsConfig.getAfternoonStart()).thenReturn("14:00");
    when(pointsConfig.getAfternoonEnd()).thenReturn("16:00");
  }

  @Test
  public void testCalculatePoints() throws ValidationException {

    Receipt receipt = new ReceiptServiceTest.ReceiptBuilder()
            .retailer("Target")
            .purchaseDate(LocalDate.of(2022, 1, 1))
            .purchaseTime(LocalTime.of(13, 1))
            .total("35.35")
            .items(Arrays.asList(
                    new Item("Mountain Dew 12PK", "6.49", 0),
                    new Item("Emils Cheese Pizza", "12.25", 0),
                    new Item("Knorr Creamy Chicken", "1.26", 0),
                    new Item("Doritos Nacho Cheese", "3.35", 0),
                    new Item("   Klarbrunn 12-PK 12 FL OZ  ", "12.00", 0)
            ))
            .build();

    int points = pointsCalculator.calculateTotalPoints(receipt);

    assertEquals(28, points);
  }

  @Test
  public void testCalculatePoints2() throws ValidationException {

    Receipt receipt = new ReceiptServiceTest.ReceiptBuilder()
            .retailer("M&M Corner Market")
            .purchaseDate(LocalDate.of(2022, 3, 20))
            .purchaseTime(LocalTime.of(14, 33))
            .total("9.00")
            .items(Arrays.asList(
                    new Item("Gatorade", "2.25", 0),
                    new Item("Gatorade", "2.25", 0),
                    new Item("Gatorade", "2.25", 0),
                    new Item("Gatorade", "2.25", 0)
            ))
            .build();

    int points = pointsCalculator.calculateTotalPoints(receipt);

    assertEquals(109, points);
  }

  @Test
  public void testCalculatePoints3() throws ValidationException {

    Receipt receipt = new ReceiptServiceTest.ReceiptBuilder()
            .retailer("M&M Corner Market)))(((*&^%$#@!")
            .purchaseDate(LocalDate.of(2022, 3, 20))
            .purchaseTime(LocalTime.of(14, 33))
            .total("9.00")
            .items(Arrays.asList(
                    new Item("Gatorade", "2.25", 0),
                    new Item("Gatorade", "2.25", 0),
                    new Item("Gatorade", "2.25", 0),
                    new Item("Gatorade", "2.25", 0)
            ))
            .build();

    int points = pointsCalculator.calculateTotalPoints(receipt);

    assertEquals(109, points);
  }

  @Test
  public void testCalculatePoints4() throws ValidationException {

    Receipt receipt = new ReceiptServiceTest.ReceiptBuilder()
            .retailer("M&M Corner Market)))(((*&^%$#@!")
            .purchaseDate(LocalDate.of(2022, 3, 20))
            .purchaseTime(LocalTime.of(14, 33))
            .total("9.01")
            .items(Arrays.asList(
                    new Item("Gatorade", "2.26", 0),
                    new Item("Gatorade", "2.25", 0),
                    new Item("Gatorade", "2.25", 0),
                    new Item("Gatorade", "2.25", 0)
            ))
            .build();

    int points = pointsCalculator.calculateTotalPoints(receipt);

    assertEquals(34, points);
  }

  @Test
  public void testCalculatePoints5() throws ValidationException {

    Receipt receipt = new ReceiptServiceTest.ReceiptBuilder()
            .retailer("M&M Corner Market)))(((*&^%$#@!")
            .purchaseDate(LocalDate.of(2022, 3, 20))
            .purchaseTime(LocalTime.of(14, 33))
            .total("11.25")
            .items(Arrays.asList(
                    new Item("Gatorade", "2.25", 0),
                    new Item("Gatorade", "2.25", 0),
                    new Item("Gatorade", "2.25", 0),
                    new Item("Gatorade", "2.25", 0),
                    new Item("Gatorade", "2.25", 0)
            ))
            .build();

    int points = pointsCalculator.calculateTotalPoints(receipt);

    assertEquals(59, points);
  }

  @Test
  public void testCalculatePoints6() throws ValidationException {

    Receipt receipt = new ReceiptServiceTest.ReceiptBuilder()
            .retailer("M&M Corner Market)))(((*&^%$#@!")
            .purchaseDate(LocalDate.of(2022, 3, 20))
            .purchaseTime(LocalTime.of(14, 33))
            .total("13.50")
            .items(Arrays.asList(
                    new Item("Gatorade", "2.25", 0),
                    new Item("Gatorade", "2.25", 0),
                    new Item("Gatorade", "2.25", 0),
                    new Item("Gatorade", "2.25", 0),
                    new Item("Gatorade", "2.25", 0),
                    new Item("Gatorade", "2.25", 0)
            ))
            .build();

    int points = pointsCalculator.calculateTotalPoints(receipt);

    assertEquals(64, points);
  }

  @Test
  public void testCalculatePoints7() throws ValidationException {

    Receipt receipt = new ReceiptServiceTest.ReceiptBuilder()
            .retailer("M&M Corner Market)))(((*&^%$#@!")
            .purchaseDate(LocalDate.of(2022, 3, 20))
            .purchaseTime(LocalTime.of(14, 33))
            .total("13.50")
            .items(Arrays.asList(
                    new Item("Gatorade", "2.25", 0),
                    new Item("Gatorade", "2.25", 0),
                    new Item("Gatorade", "2.25", 0),
                    new Item("Gatorade", "2.25", 0),
                    new Item("Gatorade", "2.25", 0),
                    new Item("Gatoradee    ", "2.25", 0)
            ))
            .build();

    int points = pointsCalculator.calculateTotalPoints(receipt);

    assertEquals(65, points);
  }

  @Test
  public void testCalculatePoints8() throws ValidationException {

    Receipt receipt = new ReceiptServiceTest.ReceiptBuilder()
            .retailer("M&M Corner Market)))(((*&^%$#@!")
            .purchaseDate(LocalDate.of(2022, 3, 20))
            .purchaseTime(LocalTime.of(14, 33))
            .total("13.50")
            .items(Arrays.asList(
                    new Item("Gatorade", "2.25", 0),
                    new Item("Gatorade", "2.25", 0),
                    new Item("Gatorade ", "2.25", 0),
                    new Item("Gatorade  ", "2.25", 0),
                    new Item("Gatorade   ", "2.25", 0),
                    new Item("Gatoradee    ", "2.25", 0)
            ))
            .build();

    int points = pointsCalculator.calculateTotalPoints(receipt);

    assertEquals(65, points);
  }

  @Test
  public void testCalculatePoints9() throws ValidationException {

    Receipt receipt = new ReceiptServiceTest.ReceiptBuilder()
            .retailer("M&M Corner Market)))(((*&^%$#@!")
            .purchaseDate(LocalDate.of(2022, 3, 21))
            .purchaseTime(LocalTime.of(14, 33))
            .total("13.50")
            .items(Arrays.asList(
                    new Item("Gatorade", "2.25", 0),
                    new Item("Gatorade", "2.25", 0),
                    new Item("Gatorade ", "2.25", 0),
                    new Item("Gatorade  ", "2.25", 0),
                    new Item("Gatorade   ", "2.25", 0),
                    new Item("Gatoradee    ", "2.25", 0)
            ))
            .build();

    int points = pointsCalculator.calculateTotalPoints(receipt);

    assertEquals(71, points);
  }

  @Test
  public void testCalculatePoints10() throws ValidationException {

    Receipt receipt = new ReceiptServiceTest.ReceiptBuilder()
            .retailer("M&M Corner Market)))(((*&^%$#@!")
            .purchaseDate(LocalDate.of(2022, 3, 21))
            .purchaseTime(LocalTime.of(16, 01))
            .total("13.50")
            .items(Arrays.asList(
                    new Item("Gatorade", "2.25", 0),
                    new Item("Gatorade", "2.25", 0),
                    new Item("Gatorade ", "2.25", 0),
                    new Item("Gatorade  ", "2.25", 0),
                    new Item("Gatorade   ", "2.25", 0),
                    new Item("Gatoradee    ", "2.25", 0)
            ))
            .build();

    int points = pointsCalculator.calculateTotalPoints(receipt);

    assertEquals(61, points);
  }

  @Test
  public void testCalculateTotalPoints() throws ValidationException {
    Receipt receipt1 = new ReceiptServiceTest.ReceiptBuilder()
            .retailer("M&M Corner Market")
            .purchaseDate(LocalDate.of(2022, 1, 1))
            .purchaseTime(LocalTime.of(13, 1))
            .total("6.49")
            .items(Arrays.asList(
                    new Item("Milk 1L", "3.99", 0),
                    new Item("Bread", "2.50", 0)
            ))
            .build();

    Receipt receipt2 = new ReceiptServiceTest.ReceiptBuilder()
            .retailer("Best Buy")
            .purchaseDate(LocalDate.of(2022, 1, 1))
            .purchaseTime(LocalTime.of(15, 0))
            .total("50.00")
            .items(Arrays.asList(
                    new Item("Laptop", "1200.00", 0),
                    new Item("Mouse", "20.00", 0),
                    new Item("Keyboard", "50.00", 0),
                    new Item("Monitor", "300.00", 0)
            ))
            .build();

    int points1 = pointsCalculator.calculateTotalPoints(receipt1);
    int points2 = pointsCalculator.calculateTotalPoints(receipt2);

    assertEquals(25, points1);
    assertEquals(348, points2);
  }

  @Test
  public void testCalculateTotalPoints2() throws ValidationException {
    Receipt receipt1 = new ReceiptServiceTest.ReceiptBuilder()
            .retailer("M&M Corner Market")
            .purchaseDate(LocalDate.of(2022, 1, 1))
            .purchaseTime(LocalTime.of(13, 1))
            .total("6.49")
            .items(Arrays.asList(
                    new Item("Milk 1L", "3.99", 0),
                    new Item("Bread", "2.50", 0)
            ))
            .build();
    int points1 = pointsCalculator.calculateTotalPoints(receipt1);
    assertEquals(25, points1);

    Receipt receipt2 = new ReceiptServiceTest.ReceiptBuilder()
            .retailer("Grocery Store")
            .purchaseDate(LocalDate.of(2022, 2, 2))
            .purchaseTime(LocalTime.of(14, 30))
            .total("10.00") // Round dollar amount
            .items(Arrays.asList(
                    new Item("Apple", "1.50", 0),
                    new Item("Orange", "2.00", 0),
                    new Item("Banana", "1.00", 0),
                    new Item("Bread", "5.50", 0)
            ))
            .build();
    int points2 = pointsCalculator.calculateTotalPoints(receipt2);
    assertEquals(109, points2);

    Receipt receipt3 = new ReceiptServiceTest.ReceiptBuilder()
            .retailer("Super Mart")
            .purchaseDate(LocalDate.of(2022, 3, 3))
            .purchaseTime(LocalTime.of(15, 30))
            .total("7.50")
            .items(Arrays.asList(
                    new Item("Eggs", "3.00", 0),
                    new Item("Milk", "2.50", 0),
                    new Item("Bread", "2.00", 0)
            ))
            .build();
    int points3 = pointsCalculator.calculateTotalPoints(receipt3);
    assertEquals(55, points3);

    Receipt receipt4 = new ReceiptServiceTest.ReceiptBuilder()
            .retailer("Corner Store")
            .purchaseDate(LocalDate.of(2022, 4, 4))
            .purchaseTime(LocalTime.of(15, 30))
            .total("15.00")
            .items(Arrays.asList(
                    new Item("Chocolate", "7.50", 0),
                    new Item("Cookies", "5.50", 0)
            ))
            .build();
    int points4 = pointsCalculator.calculateTotalPoints(receipt4);
    assertEquals(103, points4);
  }

  @Test
  public void testCalculateTotalPoints3() throws ValidationException {
    Receipt receipt1 = new ReceiptServiceTest.ReceiptBuilder()
            .retailer("M&M Corner Market")
            .purchaseDate(LocalDate.of(2022, 5, 15))
            .purchaseTime(LocalTime.of(14, 30))
            .total("8.49")
            .items(Arrays.asList(
                    new Item("Mountain Dew 12PK", "5.99", 0),
                    new Item("Lays Chips", "2.50", 0)
            ))
            .build();
    assertEquals(35, pointsCalculator.calculateTotalPoints(receipt1));

    Receipt receipt2 = new ReceiptServiceTest.ReceiptBuilder()
            .retailer("M*(!M Corner Market")
            .purchaseDate(LocalDate.of(2022, 5, 15))
            .purchaseTime(LocalTime.of(14, 30))
            .total("8.49")
            .items(Arrays.asList(
                    new Item("Mountain Dew 12PK", "5.99", 0),
                    new Item("Lays Chips", "2.50", 0)
            ))
            .build();

    assertEquals(35, pointsCalculator.calculateTotalPoints(receipt2));
  }
}

