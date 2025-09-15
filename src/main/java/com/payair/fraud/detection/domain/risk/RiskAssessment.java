package com.payair.fraud.detection.domain.risk;

import java.util.ArrayList;
import java.util.List;

import static com.payair.fraud.detection.domain.risk.RiskLevel.CRITICAL;
import static com.payair.fraud.detection.domain.risk.RiskLevel.HIGH;
import static com.payair.fraud.detection.domain.risk.RiskLevel.LOW;
import static com.payair.fraud.detection.domain.risk.RiskLevel.MEDIUM;
import static com.payair.fraud.detection.domain.risk.RiskLevel.SAFE;
import static java.util.Collections.emptyList;

public class RiskAssessment {
    private RiskLevel riskLevel = SAFE;
    private List<String> riskAssessment = emptyList();

    public void increaseRiskLevel(String assessment) {
        riskLevel = nextRiskLevel();
        riskAssessment.add(assessment);
    }

    public int getRiskLevel() {
        return riskLevel.value;
    }

    public List<String> getRiskAssessment() {
        return new ArrayList<>(riskAssessment);
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
