package com.fetch.receiptprocessor.dao;

/**
 * Interface for receipt data access operations.
 * Defines methods for saving points associated with receipts and retrieving points for a given receipt ID.
 */
public interface IReceiptDao {

  /**
   * Saves the specified number of points and generates a unique identifier for the receipt.
   *
   * @param points the number of points to be saved
   * @return a unique string identifier (receipt ID) under which the points are saved
   */
  String saveReceiptPoints(int points);

  /**
   * Retrieves the number of points associated with a given receipt ID.
   *
   * @param receiptId the unique identifier of the receipt whose points are to be fetched
   * @return the number of points associated with the specified receipt ID, or {@code null} if no such receipt exists
   */
  Integer getReceiptPoints(String receiptId);
}
