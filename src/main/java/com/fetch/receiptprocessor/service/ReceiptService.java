package com.fetch.receiptprocessor.service;

import com.fetch.receiptprocessor.dao.IReceiptDao;
import com.fetch.receiptprocessor.exception.AbsentException;
import com.fetch.receiptprocessor.exception.ValidationException;
import com.fetch.receiptprocessor.model.Item;
import com.fetch.receiptprocessor.model.Points;
import com.fetch.receiptprocessor.model.Receipt;
import com.fetch.receiptprocessor.model.ReceiptResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Service class for handling receipt-related operations.
 * This includes saving receipt points and retrieving points for a receipt.
 */
@Service
public class ReceiptService implements IReceiptService {

  private static final Logger log = LoggerFactory.getLogger(ReceiptService.class);

  final IReceiptDao receiptDao;

  PointsCalculator pointsCalculator;

  /**
   * Constructs a new ReceiptService with the specified receipt DAO.
   *
   * @param receiptDao the DAO to be used for receipt data access operations
   */
  public ReceiptService(IReceiptDao receiptDao, PointsCalculator pointsCalculator) {
    this.receiptDao = receiptDao;
    this.pointsCalculator = pointsCalculator;
  }

  /**
   * Saves the points calculated for a receipt and returns a response with the receipt ID.
   *
   * @param receipt the receipt for which points are to be calculated and saved
   * @return a {@link ReceiptResponse} containing the ID of the receipt for which points were saved
   * @throws RuntimeException if there is an error during the saving process
   */
  @Override
  public ReceiptResponse saveReceiptPoints(Receipt receipt) throws ValidationException {

    validate(receipt);

    log.info("Saving receipt points for receipt: {}", receipt);
    // Parsing the date string into a LocalDate object
    receipt.setDate(LocalDate.parse(receipt.getPurchaseDate()));
    // Parsing the time string into a LocalTime object
    receipt.setTime(LocalTime.parse(receipt.getPurchaseTime()));
    int totalPoints = pointsCalculator.calculateTotalPoints(receipt);
    String id = receiptDao.saveReceiptPoints(totalPoints);
    ReceiptResponse response = new ReceiptResponse(id);
    log.info("Successfully saved receipt points. Response ID: {}", id);
    return response;
  }

  private static void validate(Receipt receipt) throws ValidationException {
    try {
      receipt.setTotalDbl(Double.parseDouble(receipt.getTotal()));
    } catch (IllegalArgumentException e) {
      throw new ValidationException("total is not a valid double");
    }

    if(receipt.getTotalDbl() <= 0) {
      throw new ValidationException("Invalid - Total can't be 0 or negative");
    }

    double sum = 0;
    for(Item i : receipt.getItems()) {
      try {
        i.setPriceDbl(Double.parseDouble(i.getPrice()));
      } catch (IllegalArgumentException e) {
        throw new ValidationException("item price is not a valid double");
      }

      if(i.getPriceDbl() <= 0) {
        throw new ValidationException("Invalid - Item price can't be 0 or negative");
      }

      sum += i.getPriceDbl();
    }

    double tolerance = 0.01; // Tolerance for comparison
    if (Math.abs(sum - receipt.getTotalDbl()) > tolerance) {
      throw new ValidationException("Invalid - Sum of item prices doesn't add up to total");
    }
  }

  /**
   * Retrieves the points associated with a given receipt ID.
   *
   * @param receiptId the ID of the receipt whose points are to be fetched
   * @return a {@link Points} object containing the points for the specified receipt
   * @throws AbsentException  if no points are found for the given receipt ID
   * @throws RuntimeException if there is an error during the retrieval process
   */
  @Override
  public Points getReceiptPoints(String receiptId) throws AbsentException {

    log.info("Retrieving points for receipt ID: {}", receiptId);
    try {
      Integer points = receiptDao.getReceiptPoints(receiptId);
      if (points == null) {
        log.error("No points found for receipt ID: {}", receiptId);
        throw new AbsentException(receiptId);
      }
      log.info("Successfully retrieved points for receipt ID: {}", receiptId);
      return new Points(points);
    } catch (AbsentException e) {
      log.error("AbsentException for receipt ID: {}: {}", receiptId, e.getMessage(), e);
      throw e;
    } catch (Exception e) {
      log.error("Error retrieving points for receipt ID: {}: {}", receiptId, e.getMessage(), e);
      throw e;
    }
  }
}
