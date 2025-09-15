package com.payair.fraud.detection.domain.risk;

public enum RiskLevel {
    SAFE(1),
    LOW(2),
    MEDIUM(3),
    HIGH(4),
    CRITICAL(5);

    public final int value;

    RiskLevel(int i) {
        value = i;
    }
}
