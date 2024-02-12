package com.fetch.receiptprocessor.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "points")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PointsConfig {

  private int retailerName;
  private int roundDollar;
  private int multipleOfQuarter;
  private int perTwoItems;
  private double multipleOfThreeItemDescription;
  private int oddDay;
  private int afternoonBonus;
  private String afternoonStart;
  private String afternoonEnd;
}