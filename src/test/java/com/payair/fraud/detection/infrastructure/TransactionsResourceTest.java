package com.payair.fraud.detection.infrastructure;

import com.payair.fraud.detection.infrastructure.endpoint.TransactionsResource;
import com.payair.fraud.detection.infrastructure.endpoint.dto.TransactionsRequest;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.Header;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(TransactionsResource.class)
public class TransactionsResourceTest {

    private final Header contentJsonHeader = new Header("Content-Type", "application/json");

    @Test
    public void testTransactions() {
        TransactionsRequest request = new TransactionsRequest("BIN");

        given().body(request)
                .header(contentJsonHeader)
                .when()
                .post()
                .then()
                .statusCode(200);
    }
}
