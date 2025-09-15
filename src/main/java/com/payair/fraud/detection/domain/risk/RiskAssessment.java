package com.payair.fraud.detection.domain.risk;

import java.util.ArrayList;
import java.util.List;

import static com.payair.fraud.detection.domain.risk.RiskLevel.CRITICAL;
import static com.payair.fraud.detection.domain.risk.RiskLevel.HIGH;
import static com.payair.fraud.detection.domain.risk.RiskLevel.LOW;
import static com.payair.fraud.detection.domain.risk.RiskLevel.MEDIUM;
import static com.payair.fraud.detection.domain.risk.RiskLevel.SAFE;
import static java.util.Collections.unmodifiableList;

public class RiskAssessment {
    private RiskLevel riskLevel = SAFE;
    private final List<String> riskAssessment = new ArrayList<>();

    public void increaseRiskLevel(String assessment) {
        riskLevel = nextRiskLevel();
        riskAssessment.add(assessment);
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public List<String> getRiskAssessment() {
        return unmodifiableList(riskAssessment);
    }

    private RiskLevel nextRiskLevel() {
        return switch (riskLevel) {
            case SAFE -> LOW;
            case LOW -> MEDIUM;
            case MEDIUM -> HIGH;
            case HIGH, CRITICAL -> CRITICAL;
        };
    }
}
