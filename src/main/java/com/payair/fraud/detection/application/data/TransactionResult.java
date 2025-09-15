package com.payair.fraud.detection.application.data;

import com.payair.fraud.detection.domain.risk.RiskLevel;

import java.util.List;

public record TransactionResult(RiskLevel riskLevel, List<String> assessment) {
}
