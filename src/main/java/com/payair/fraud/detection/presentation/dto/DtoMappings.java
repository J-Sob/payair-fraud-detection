package com.payair.fraud.detection.presentation.dto;

import com.payair.fraud.detection.application.data.TransactionData;
import com.payair.fraud.detection.application.data.TransactionResult;
import com.payair.fraud.detection.domain.shared.ISONumericCountryCode;
import com.payair.fraud.detection.domain.transaction.BIN;
import com.payair.fraud.detection.domain.transaction.TransactionAmount;

public class DtoMappings {

    public static TransactionsResponse mapToResponse(TransactionResult transactionResult) {
        return new TransactionsResponse(transactionResult.riskLevel().value, transactionResult.assessment());
    }

    public static TransactionData mapFromRequest(TransactionsRequest transactionsRequest) {
        BIN bin = new BIN(transactionsRequest.bin());
        ISONumericCountryCode countryCode = new ISONumericCountryCode(transactionsRequest.countryCode());
        TransactionAmount transactionAmount = new TransactionAmount(transactionsRequest.transactionAmount());
        return new TransactionData(bin, countryCode, transactionAmount);
    }
}
