package com.payair.fraud.detection.presentation.dto;

public record TransactionsRequest(String BIN, String countryCode, Double transactionAmount) {}
