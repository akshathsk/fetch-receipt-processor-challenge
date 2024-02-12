package com.fetch.receiptprocessor.service;

import com.fetch.receiptprocessor.exception.AbsentException;
import com.fetch.receiptprocessor.exception.ValidationException;
import com.fetch.receiptprocessor.model.Points;
import com.fetch.receiptprocessor.model.Receipt;
import com.fetch.receiptprocessor.model.ReceiptResponse;

/**
 * Interface defining the service layer for handling operations related to receipts and their points.
 * Specifies methods for saving points based on receipt data and retrieving points for a specific receipt ID.
 */
public interface IReceiptService {

  /**
   * Processes the given receipt, calculates its points based on predefined criteria, and saves those points.
   * Generates a unique identifier for the receipt and returns a response containing this identifier.
   *
   * @param receipt The receipt object containing the necessary data for processing and point calculation.
   * @return A {@link ReceiptResponse} object containing the unique identifier of the processed receipt.
   */
  ReceiptResponse saveReceiptPoints(Receipt receipt) throws ValidationException;

  /**
   * Retrieves the total points associated with a given receipt ID.
   *
   * @param receiptId The unique identifier of the receipt whose points are to be retrieved.
   * @return A {@link Points} object containing the total points associated with the specified receipt ID.
   * @throws AbsentException If no receipt with the given ID exists or if no points are associated with it.
   */
  Points getReceiptPoints(String receiptId) throws AbsentException;
}
