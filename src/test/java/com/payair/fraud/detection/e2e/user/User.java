package com.payair.fraud.detection.e2e.user;

import com.payair.fraud.detection.e2e.transaction.TransactionFlowValidation;
import com.payair.fraud.detection.jwt.JwtGenerator;
import com.payair.fraud.detection.presentation.dto.TransactionsRequest;
import io.restassured.response.ValidatableResponse;

import static com.payair.fraud.detection.e2e.client.TransactionClient.sendAuthorizedTransactionRequest;
import static com.payair.fraud.detection.e2e.client.TransactionClient.sendUnauthorizedTransactionRequest;

public class User {

    private static final TransactionsRequest staticTransactionRequest = new TransactionsRequest(
            "99875393",
            "880",
            450d
    );

    private String authToken;

    public User isAuthenticated() {
        authToken = JwtGenerator.generateJwt();
        return this;
    }

    public TransactionFlowValidation sendsValidTransactionRequest() {
        ValidatableResponse validatableResponse = authToken != null ? sendAuthorizedTransactionRequest(staticTransactionRequest, authToken) : sendUnauthorizedTransactionRequest(staticTransactionRequest);

        return new TransactionFlowValidation(validatableResponse);
    }
}
