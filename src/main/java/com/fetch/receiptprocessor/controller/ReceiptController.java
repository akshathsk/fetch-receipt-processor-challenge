package com.fetch.receiptprocessor.controller;

import com.fetch.receiptprocessor.exception.AbsentException;
import com.fetch.receiptprocessor.exception.ValidationException;
import com.fetch.receiptprocessor.model.Points;
import com.fetch.receiptprocessor.model.Receipt;
import com.fetch.receiptprocessor.model.ReceiptResponse;
import com.fetch.receiptprocessor.service.IReceiptService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling receipt processing requests.
 * Provides endpoints for processing receipts and fetching points for a given receipt.
 */
@Slf4j
@RestController
@RequestMapping("/receipts")
public class ReceiptController {

  final
  IReceiptService receiptService;

  /**
   * Constructs a new ReceiptController with the given receipt service.
   *
   * @param receiptService the service to handle receipt operations
   */
  public ReceiptController(IReceiptService receiptService) {
    this.receiptService = receiptService;
  }

  /**
   * Processes the given receipt and saves its points.
   *
   * @param receipt the receipt to process
   * @return a {@link ReceiptResponse} containing details of the processed receipt
   */
  @PostMapping("/process")
  public ReceiptResponse processReceipts(@Valid @RequestBody Receipt receipt) throws ValidationException {

    log.info("Processing receipt with details: {}", receipt);
    ReceiptResponse response = receiptService.saveReceiptPoints(receipt);
    log.info("Processed receipt successfully with response: {}", response);
    return response;
  }

  /**
   * Fetches the points associated with a given receipt ID.
   *
   * @param receiptId the ID of the receipt whose points are to be fetched
   * @return a {@link Points} object containing the points for the specified receipt
   * @throws AbsentException if no points are found for the given receipt ID
   */
  @GetMapping("/{id}/points")
  public Points getReceiptPoints(@PathVariable("id") String receiptId) throws AbsentException {

    log.info("Fetching points for receiptId: {}", receiptId);
    Points points = receiptService.getReceiptPoints(receiptId);
    log.info("Fetched points successfully for receiptId: {}", receiptId);
    return points;
  }
}
