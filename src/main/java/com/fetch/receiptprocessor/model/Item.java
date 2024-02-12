package com.fetch.receiptprocessor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

  @Schema(description = "The short description of the item. It must match a specific pattern.",
          example = "Milk 1L", maxLength = 100)
  @NotNull(message = "shortDescription is required")
  @Pattern(regexp = "^[\\w\\s\\-]+$", message = "shortDescription format is invalid")
  private String shortDescription;

  @Schema(description = "The price of the item in a decimal format with two decimal places.",
          example = "3.99", pattern = "^\\d+\\.\\d{2}$")
  @NotNull(message = "price is required")
  @Pattern(regexp = "^\\d+\\.\\d{2}$", message = "price format is invalid")
  private String price;

  @JsonIgnore
  double priceDbl;
}