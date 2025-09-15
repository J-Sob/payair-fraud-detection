package com.payair.fraud.detection.application;

import com.payair.fraud.detection.application.data.TransactionData;
import com.payair.fraud.detection.application.data.TransactionResult;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FraudDetectionService {

    public TransactionResult verifyTransaction(TransactionData transactionData) {
        return null;
    }
}
