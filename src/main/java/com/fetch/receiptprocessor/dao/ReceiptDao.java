package com.fetch.receiptprocessor.dao;

import com.fetch.receiptprocessor.dao.data.Store;
import com.fetch.receiptprocessor.util.Generator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;


/**
 * Data Access Object (DAO) for managing receipt points storage and retrieval.
 * Utilizes an in-memory store to keep track of points associated with receipt IDs.
 */
@Slf4j
@Repository
public class ReceiptDao implements IReceiptDao {

  private final Store<String, Integer> receiptPointsStore = new Store<>();

  /**
   * Saves the specified number of points against a newly generated receipt ID.
   *
   * @param points the number of points to save
   * @return the generated receipt ID under which the points were saved
   */
  @Override
  public String saveReceiptPoints(int points) {

    String receiptId = Generator.generateReceiptId();
    receiptPointsStore.put(receiptId, points);
    log.info("Saved {} points for receipt ID: {}", points, receiptId);
    return receiptId;
  }

  /**
   * Retrieves the number of points associated with a given receipt ID.
   *
   * @param receiptId the ID of the receipt for which to retrieve points
   * @return the number of points associated with the receipt ID, or {@code null} if no points were found
   */
  @Override
  public Integer getReceiptPoints(String receiptId) {

    Integer points = receiptPointsStore.get(receiptId);
    if (points != null) {
      log.info("Retrieved {} points for receipt ID: {}", points, receiptId);
    } else {
      log.warn("No points found for receipt ID: {}", receiptId);
    }
    return points;
  }
}
