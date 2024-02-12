package com.fetch.receiptprocessor.service;

import com.fetch.receiptprocessor.exception.ValidationException;
import com.fetch.receiptprocessor.model.Item;
import com.fetch.receiptprocessor.model.Receipt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Slf4j
@Component
public class PointsCalculator {

  private static PointsConfig pointsConfig;

  @Autowired
  public PointsCalculator(PointsConfig config) {
    PointsCalculator.pointsConfig = config;
  }

  public int calculateTotalPoints(Receipt receipt) throws ValidationException {
    int totalPoints = 0;

    // Rule 1: One point for every alphanumeric character in the retailer name
    totalPoints += calculateRetailerNamePoints(receipt.getRetailer()); //6

    // Rule 2: 50 points if the total is a round dollar amount with no cents
    double totalValue;
    try {
      totalValue = Double.parseDouble(receipt.getTotal());
    } catch (IllegalArgumentException e) {
      throw new ValidationException("total is not a valid double");
    }
    totalPoints += totalValue - (int)totalValue == 0 ? pointsConfig.getRoundDollar() : 0; // 6

    // Rule 3: 25 points if the total is a multiple of 0.25
    totalPoints += ((totalValue * 4) - (int) (totalValue * 4) == 0) ? pointsConfig.getMultipleOfQuarter() : 0; // 6

    // Rule 4: 5 points for every two items on the receipt
    totalPoints += (receipt.getItems().size() / 2) * pointsConfig.getPerTwoItems(); // 16

    // Rule 5: If the trimmed length of the item description is a multiple of 3,
    // multiply the price by 0.2 and round up to the nearest integer.
    // The result is the number of points earned
    for (Item item : receipt.getItems()) {
      totalPoints += calculateItemPoints(item); // 22
    }

    // Rule 6: 6 points if the day in the purchase date is odd
    totalPoints += (receipt.getDate().getDayOfMonth() % 2 != 0) ? pointsConfig.getOddDay() : 0;

    // Rule 7: 10 points if the time of purchase is after 2:00pm and before 4:00pm
    LocalTime purchaseTime = receipt.getTime();
    LocalTime afternoonStart = LocalTime.parse(pointsConfig.getAfternoonStart());
    LocalTime afternoonEnd = LocalTime.parse(pointsConfig.getAfternoonEnd());
    if (purchaseTime.isAfter(afternoonStart) && purchaseTime.isBefore(afternoonEnd)) {
      totalPoints += pointsConfig.getAfternoonBonus(); // 28
    }

    return totalPoints;
  }

  private int calculateRetailerNamePoints(String retailer) {
    return (int) retailer.chars().filter(Character::isLetterOrDigit).count() * pointsConfig.getRetailerName();
  }

  private int calculateItemPoints(Item item) {
    try {
      double price = Double.parseDouble(item.getPrice());
      if (item.getShortDescription().trim().length() % 3 == 0) {
        return (int) Math.ceil(price * pointsConfig.getMultipleOfThreeItemDescription());
      }
    } catch (NumberFormatException e) {
      log.error("Error parsing the price: " + e.getMessage());
    }
    return 0;
  }
}
