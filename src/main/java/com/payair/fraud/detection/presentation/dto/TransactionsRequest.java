package com.payair.fraud.detection.presentation.dto;

public record TransactionsRequest(String bin, String countryCode, Double transactionAmount) {
}
