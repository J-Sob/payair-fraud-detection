package com.payair.fraud.detection.e2e.client;

import com.payair.fraud.detection.presentation.TransactionsResource;
import com.payair.fraud.detection.presentation.dto.TransactionsRequest;
import io.restassured.http.Header;
import io.restassured.response.ValidatableResponse;

import java.util.UUID;

import static io.restassured.RestAssured.given;

public class TransactionClient {

    private static final String REQUEST_ID_HEADER = "X-REQUESTID";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final Header APPLICATION_JSON = new Header("Content-Type", "application/json");

    public static ValidatableResponse sendAuthorizedTransactionRequest(TransactionsRequest transactionsRequest, String authToken) {
        String requestId = UUID.randomUUID().toString();

        return given()
                .body(transactionsRequest)
                .header(AUTHORIZATION_HEADER, "Bearer " + authToken)
                .header(REQUEST_ID_HEADER, requestId)
                .header(APPLICATION_JSON)
                .when()
                .post(TransactionsResource.TRANSACTIONS_PATH)
                .then()
                .header(REQUEST_ID_HEADER, requestId);
    }

    public static ValidatableResponse sendUnauthorizedTransactionRequest(TransactionsRequest transactionsRequest) {
        return given()
                .body(transactionsRequest)
                .header(APPLICATION_JSON)
                .when()
                .post(TransactionsResource.TRANSACTIONS_PATH)
                .then();
    }
}
