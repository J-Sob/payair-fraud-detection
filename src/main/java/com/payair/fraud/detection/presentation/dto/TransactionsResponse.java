package com.payair.fraud.detection.presentation.dto;

import java.util.List;

public record TransactionsResponse(int riskLevel, List<String> assessment) {}
