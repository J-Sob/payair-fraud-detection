package com.payair.fraud.detection.presentation.dto;

import com.payair.fraud.detection.application.data.TransactionData;
import com.payair.fraud.detection.application.data.TransactionResult;
import com.payair.fraud.detection.domain.exceptions.WrongDataFormatException;
import com.payair.fraud.detection.domain.risk.RiskLevel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.payair.fraud.detection.fixtures.TransactionFixtures.safeTransactionResult;
import static com.payair.fraud.detection.fixtures.TransactionFixtures.transactionResult;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DtoMappingsTest {

    @ParameterizedTest
    @MethodSource("transactionResults")
    public void shouldMapTransactionResult(TransactionResult transactionResult) {
        TransactionsResponse response = DtoMappings.mapToResponse(transactionResult);

        assertEquals(transactionResult.riskLevel().value, response.riskLevel());
        assertEquals(transactionResult.assessment(), response.assessment());
    }

    @Test
    public void shouldMapTransactionRequest() {
        TransactionsRequest request = new TransactionsRequest("123456", "616", 320d);

        TransactionData transactionData = DtoMappings.mapFromRequest(request);

        assertEquals(request.BIN(), transactionData.bin().value());
        assertEquals(request.countryCode(), transactionData.country().value());
        assertEquals(request.transactionAmount(), transactionData.amount().value());
    }

    @ParameterizedTest
    @MethodSource("invalidRequests")
    public void shouldFailToMapTransactionRequest(TransactionsRequest request, String exceptionMessage) {
        String message = assertThrows(WrongDataFormatException.class, () -> DtoMappings.mapFromRequest(request)).getMessage();

        assertTrue(message.startsWith(exceptionMessage));
    }

    private static Stream<Arguments> transactionResults() {
        return Stream.of(
                Arguments.of(safeTransactionResult()),
                Arguments.of(transactionResult(RiskLevel.LOW, "single assessment")),
                Arguments.of(transactionResult(RiskLevel.MEDIUM, "first assessment", "second assessment")),
                Arguments.of(transactionResult(RiskLevel.HIGH, "single assessment")),
                Arguments.of(transactionResult(RiskLevel.CRITICAL, "first assessment", "second assessment"))
        );
    }

    private static Stream<Arguments> invalidRequests() {
        return Stream.of(
                Arguments.of(new TransactionsRequest("invalid", "616", 320d), "Invalid bin value"),
                Arguments.of(new TransactionsRequest("123456", "invalid", 320d), "Invalid country code value"),
                Arguments.of(new TransactionsRequest("123456", "616", -450d), "Amount value cannot be negative")
        );
    }
}
