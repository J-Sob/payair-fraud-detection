package com.payair.fraud.detection.infrastructure.endpoint.dto;

import com.payair.fraud.detection.domain.FraudAttestation;
import com.payair.fraud.detection.domain.TransactionData;

public class DtoMappings {

    public static TransactionsResponse mapToResponse(FraudAttestation fraudAttestation) {
        return new TransactionsResponse();
    }

    public static TransactionData mapFromRequest(TransactionsRequest transactionsRequest) {
        return new TransactionData();
    }
}
