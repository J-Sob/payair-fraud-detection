package com.payair.fraud.detection.domain.risk;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RiskAssessmentTest {

    private final String lowRiskAssessment = "low risk";
    private final String mediumRiskAssessment = "medium risk";
    private final String highRiskAssessment = "high risk";
    private final String criticalRiskAssessment = "critical risk";

    @Test
    void shouldHaveSaveLevelWhenNotIncreased() {
        RiskAssessment assessment = new RiskAssessment();

        assertThat(assessment.getRiskLevel()).isEqualTo(RiskLevel.SAFE);
        assertThat(assessment.getRiskAssessment()).isEmpty();
    }

    @Test
    void shouldIncreaseRiskLevelAndSaveAssessment() {
        RiskAssessment assessment = new RiskAssessment();

        assessment.increaseRiskLevel(lowRiskAssessment);

        assertThat(assessment.getRiskLevel()).isEqualTo(RiskLevel.LOW);
        assertThat(assessment.getRiskAssessment())
                .containsExactly(lowRiskAssessment);
    }

    @Test
    void shouldIncreaseRiskLevelToCritical() {
        RiskAssessment assessment = new RiskAssessment();

        assessment.increaseRiskLevel(lowRiskAssessment);
        assertThat(assessment.getRiskLevel()).isEqualTo(RiskLevel.LOW);

        assessment.increaseRiskLevel(mediumRiskAssessment);
        assertThat(assessment.getRiskLevel()).isEqualTo(RiskLevel.MEDIUM);

        assessment.increaseRiskLevel(highRiskAssessment);
        assertThat(assessment.getRiskLevel()).isEqualTo(RiskLevel.HIGH);

        assessment.increaseRiskLevel(criticalRiskAssessment);
        assertThat(assessment.getRiskLevel()).isEqualTo(RiskLevel.CRITICAL);

        assertThat(assessment.getRiskAssessment())
                .containsExactlyInAnyOrder(lowRiskAssessment, mediumRiskAssessment, highRiskAssessment, criticalRiskAssessment);
    }

    @Test
    void shouldSaveAboveCriticalAssessment() {
        RiskAssessment assessment = new RiskAssessment();

        assessment.increaseRiskLevel(lowRiskAssessment);
        assessment.increaseRiskLevel(mediumRiskAssessment);
        assessment.increaseRiskLevel(highRiskAssessment);
        assessment.increaseRiskLevel(criticalRiskAssessment);

        assessment.increaseRiskLevel("even more risky than anyone could imagine");

        assertThat(assessment.getRiskLevel()).isEqualTo(RiskLevel.CRITICAL);
        assertThat(assessment.getRiskAssessment())
                .contains("even more risky than anyone could imagine");
    }

}
