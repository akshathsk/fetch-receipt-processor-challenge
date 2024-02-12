package com.fetch.receiptprocessor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Receipt {

  @Schema(description = "The name of the retailer or store the receipt is from.", example = "M&M Corner Market")
  @NotNull(message = "retailer is required")
  @Pattern(regexp = "^[\\w\\s\\-&]+$", message = "retailer format is invalid")
  private String retailer;

  @Schema(description = "The date of the purchase printed on the receipt.", example = "2022-01-01")
  @NotNull(message = "purchaseDate is required")
  @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "purchaseDate must be in the format yyyy-MM-dd")
  private String purchaseDate;

  @Schema(description = "The time of the purchase printed on the receipt. 24-hour time expected.", example = "13:01")
  @NotNull(message = "purchaseTime is required")
  @Pattern(regexp = "^([01]\\d|2[0-3]):([0-5]\\d)$", message = "purchaseTime must be in the format HH:mm")
  private String purchaseTime;

  @Schema(description = "The total amount paid on the receipt.", example = "6.49")
  @NotNull(message = "total is required")
  @Pattern(regexp = "^\\d+\\.\\d{2}$", message = "total format is invalid")
  private String total;

  @Schema(description = "The list of items purchased, with at least one item required.", required = true)
  @Valid
  @NotNull(message = "Items are required")
  @Size(min = 1, message = "There must be at least one item in items")
  private List<Item> items;

  @JsonIgnore
  transient LocalDate date;

  @JsonIgnore
  transient LocalTime time;

  @JsonIgnore
  transient double totalDbl;
}
