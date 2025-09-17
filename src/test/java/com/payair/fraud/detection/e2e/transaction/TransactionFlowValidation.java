package com.payair.fraud.detection.e2e.transaction;

import com.payair.fraud.detection.domain.risk.RiskLevel;
import com.payair.fraud.detection.presentation.dto.TransactionsResponse;
import io.restassured.response.ValidatableResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jboss.resteasy.reactive.RestResponse.StatusCode.OK;

public class TransactionFlowValidation {
    private final ValidatableResponse response;

    public TransactionFlowValidation(ValidatableResponse response) {
        this.response = response;
    }

    public TransactionFlowValidation isSuccessful() {
        returnedStatusCode(OK);
        return this;
    }

    public TransactionFlowValidation returnedStatusCode(int statusCode) {
        response.statusCode(statusCode);
        return this;
    }

    public TransactionFlowValidation riskLevelIsEqualTo(RiskLevel riskLevel) {
        TransactionsResponse body = response.extract().as(TransactionsResponse.class);
        assertThat(body.riskLevel()).isEqualTo(riskLevel.value);
        return this;
    }

    public TransactionFlowValidation assessmentContainsExactly(String... assessments) {
        TransactionsResponse body = response.extract().as(TransactionsResponse.class);
        assertThat(body.assessment()).containsExactlyInAnyOrder(assessments);
        return this;
    }
}
