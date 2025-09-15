package com.payair.fraud.detection.domain;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FraudDetectionService {

    public FraudAttestation detectFraud(TransactionData transactionData) {
        return new FraudAttestation();
    }
}
