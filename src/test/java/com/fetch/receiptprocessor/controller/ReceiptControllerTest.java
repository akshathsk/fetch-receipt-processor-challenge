package com.fetch.receiptprocessor.controller;

import com.fetch.receiptprocessor.exception.AbsentException;
import com.fetch.receiptprocessor.exception.ValidationException;
import com.fetch.receiptprocessor.model.Points;
import com.fetch.receiptprocessor.model.Receipt;
import com.fetch.receiptprocessor.model.ReceiptResponse;
import com.fetch.receiptprocessor.service.IReceiptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReceiptControllerTest {

  @Mock
  private IReceiptService receiptService;

  @InjectMocks
  private ReceiptController receiptController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testProcessReceipts() throws ValidationException {
    // Given
    Receipt receipt = new Receipt();
    ReceiptResponse expectedResponse = new ReceiptResponse("123456");

    when(receiptService.saveReceiptPoints(receipt)).thenReturn(expectedResponse);

    // When
    ReceiptResponse responseEntity = receiptController.processReceipts(receipt);

    // Then
    assertEquals(expectedResponse, responseEntity);
    verify(receiptService).saveReceiptPoints(receipt);
  }

  @Test
  void testGetReceiptPoints() throws AbsentException {
    String receiptId = "receipt123";
    Points expectedPoints = new Points(100);

    when(receiptService.getReceiptPoints(receiptId)).thenReturn(expectedPoints);

    Points responseEntity = receiptController.getReceiptPoints(receiptId);

    assertEquals(expectedPoints, responseEntity);
    verify(receiptService).getReceiptPoints(receiptId);
  }

}

