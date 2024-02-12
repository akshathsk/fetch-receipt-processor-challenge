package com.fetch.receiptprocessor.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void getReceiptNotFound() throws Exception {

    mockMvc.perform(get("/receipts/test/points")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  public void createReceipt() throws Exception {

    String receipt = "{\"retailer\":\"Target\",\"purchaseDate\":\"2022-01-01\",\"purchaseTime\":\"13:01\",\"items\":[{\"shortDescription\":\"MountainDew12PK\",\"price\":\"6.49\"},{\"shortDescription\":\"EmilsCheesePizza\",\"price\":\"12.25\"},{\"shortDescription\":\"KnorrCreamyChicken\",\"price\":\"1.26\"},{\"shortDescription\":\"DoritosNachoCheese\",\"price\":\"3.35\"},{\"shortDescription\":\"Klarbrunn12-PK12FLOZ\",\"price\":\"12.00\"}],\"total\":\"35.35\"}";

    mockMvc.perform(post("/receipts/process")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(receipt))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").exists());
  }

  @Test
  public void createAndRetrieveReceipt() throws Exception {

    String receipt = "{\"retailer\":\"Target\",\"purchaseDate\":\"2022-01-01\",\"purchaseTime\":\"13:01\",\"items\":[{\"shortDescription\":\"MountainDew 12 PK\",\"price\":\"6.49\"},{\"shortDescription\":\"Emils Cheese Pizza\",\"price\":\"12.25\"},{\"shortDescription\":\"Knorr Creamy Chicken\",\"price\":\"1.26\"},{\"shortDescription\":\"Doritos Nacho Cheese\",\"price\":\"3.35\"},{\"shortDescription\":\"   Klarbrunn 12-PK 12 FL OZ  \",\"price\":\"12.00\"}],\"total\":\"35.35\"}";

    // Perform the POST request to create a receipt
    MvcResult result = mockMvc.perform(post("/receipts/process")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(receipt))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").exists())
            .andReturn();

    // Extract the ID from the response
    String responseContent = result.getResponse().getContentAsString();
    String receiptId = extractIdFromResponse(responseContent, "id");

    // Now perform a GET request using the extracted ID
    MvcResult resultGet = mockMvc.perform(get("/receipts/" + receiptId + "/points")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()) // Or any expected status code
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn();

    String responseContentGet = resultGet.getResponse().getContentAsString();
    String score = extractIdFromResponse(responseContentGet, "points");

    Assert.assertEquals(28, Integer.parseInt(score));

  }

  @Test
  public void createAndRetrieveReceipt2() throws Exception {

    String receipt = "{\"retailer\":\"M&MCornerMarket\",\"purchaseDate\":\"2022-03-20\",\"purchaseTime\":\"14:33\",\"items\":[{\"shortDescription\":\"Gatorade\",\"price\":\"2.25\"},{\"shortDescription\":\"Gatorade\",\"price\":\"2.25\"},{\"shortDescription\":\"Gatorade\",\"price\":\"2.25\"},{\"shortDescription\":\"Gatorade\",\"price\":\"2.25\"}],\"total\":\"9.00\"}";

    // Perform the POST request to create a receipt
    MvcResult result = mockMvc.perform(post("/receipts/process")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(receipt))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").exists())
            .andReturn();

    // Extract the ID from the response
    String responseContent = result.getResponse().getContentAsString();
    String receiptId = extractIdFromResponse(responseContent, "id");

    // Now perform a GET request using the extracted ID
    MvcResult resultGet = mockMvc.perform(get("/receipts/" + receiptId + "/points")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()) // Or any expected status code
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn();

    String responseContentGet = resultGet.getResponse().getContentAsString();
    String score = extractIdFromResponse(responseContentGet, "points");

    Assert.assertEquals(109, Integer.parseInt(score));

  }

  @Test
  public void createAndRetrieveReceipt3() throws Exception {

    String receipt = "{\n    \"retailer\": \"Walgreens\",\n    \"purchaseDate\": \"2022-01-02\",\n    \"purchaseTime\": \"08:13\",\n    \"total\": \"2.65\",\n    \"items\": [\n        {\"shortDescription\": \"Pepsi - 12-oz\", \"price\": \"1.25\"},\n        {\"shortDescription\": \"Dasani\", \"price\": \"1.40\"}\n    ]\n}";

    // Perform the POST request to create a receipt
    MvcResult result = mockMvc.perform(post("/receipts/process")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(receipt))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").exists())
            .andReturn();

    // Extract the ID from the response
    String responseContent = result.getResponse().getContentAsString();
    String receiptId = extractIdFromResponse(responseContent, "id");

    // Now perform a GET request using the extracted ID
    MvcResult resultGet = mockMvc.perform(get("/receipts/" + receiptId + "/points")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()) // Or any expected status code
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn();

    String responseContentGet = resultGet.getResponse().getContentAsString();
    String score = extractIdFromResponse(responseContentGet, "points");

    Assert.assertEquals(15, Integer.parseInt(score));

  }

  @Test
  public void createAndRetrieveReceipt4() throws Exception {

    String receipt = "{\n    \"retailer\": \"Target\",\n    \"purchaseDate\": \"2022-01-02\",\n    \"purchaseTime\": \"13:13\",\n    \"total\": \"1.25\",\n    \"items\": [\n        {\"shortDescription\": \"Pepsi - 12-oz\", \"price\": \"1.25\"}\n    ]\n}";

    // Perform the POST request to create a receipt
    MvcResult result = mockMvc.perform(post("/receipts/process")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(receipt))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").exists())
            .andReturn();

    // Extract the ID from the response
    String responseContent = result.getResponse().getContentAsString();
    String receiptId = extractIdFromResponse(responseContent, "id");

    // Now perform a GET request using the extracted ID
    MvcResult resultGet = mockMvc.perform(get("/receipts/" + receiptId + "/points")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()) // Or any expected status code
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn();

    String responseContentGet = resultGet.getResponse().getContentAsString();
    String score = extractIdFromResponse(responseContentGet, "points");

    Assert.assertEquals(31, Integer.parseInt(score));

  }

  @Test
  public void createReceiptBad() throws Exception {

    String receipt = "{\n    \"retailer\": \"Target##\",\n    \"purchaseDate\": \"2022-01-02\",\n    \"purchaseTime\": \"13:13\",\n    \"total\": \"1.25\",\n    \"items\": [\n        {\"shortDescription\": \"Pepsi - 12-oz\", \"price\": \"1.25\"}\n    ]\n}";

    // Perform the POST request to create a receipt
    mockMvc.perform(post("/receipts/process")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(receipt))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  public void createReceiptBad2() throws Exception {

    String receipt = "{\n    \"retailer\": \"Target\",\n    \"purchaseDate\": \"2022-01-02\",\n    \"purchaseTime\": \"13:13\",\n    \"total\": \"1.2599\",\n    \"items\": [\n        {\"shortDescription\": \"Pepsi - 12-oz\", \"price\": \"1.25\"}\n    ]\n}";

    // Perform the POST request to create a receipt
    mockMvc.perform(post("/receipts/process")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(receipt))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  public void createReceiptBad3() throws Exception {

    String receipt = "{\n    \"retailer\": \"Target\",\n    \"purchaseDate\": \"2022-01-02\",\n    \"purchaseTime\": \"13:13\",\n    \"total\": \"1.25\",\n    \"items\": [\n        {\"shortDescription\": \"Pepsi - 12-oz$\", \"price\": \"1.25\"}\n    ]\n}";

    // Perform the POST request to create a receipt
    mockMvc.perform(post("/receipts/process")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(receipt))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  public void createReceiptBad4() throws Exception {

    String receipt = "{\n    \"retailer\": \"Target\",\n    \"purchaseDate\": \"2022-01-02\",\n    \"purchaseTime\": \"13:13\",\n    \"total\": \"1.25\",\n    \"items\": [\n        {\"shortDescription\": \"Pepsi - 12-oz$\", \"price\": \"1.45\"}\n    ]\n}";

    // Perform the POST request to create a receipt
    mockMvc.perform(post("/receipts/process")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(receipt))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  private String extractIdFromResponse(String responseContent, String path) throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode rootNode = objectMapper.readTree(responseContent);
    return rootNode.path(path).asText();
  }
}
