package com.payair.fraud.detection.application;

import com.payair.fraud.detection.application.data.TransactionData;
import com.payair.fraud.detection.application.data.TransactionResult;
import com.payair.fraud.detection.domain.account.AccountData;
import com.payair.fraud.detection.domain.account.AccountDataProvider;
import com.payair.fraud.detection.domain.policy.AssessmentPolicy;
import com.payair.fraud.detection.domain.policy.result.Failure;
import com.payair.fraud.detection.domain.policy.result.Success;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static com.payair.fraud.detection.domain.risk.RiskLevel.LOW;
import static com.payair.fraud.detection.domain.risk.RiskLevel.MEDIUM;
import static com.payair.fraud.detection.domain.risk.RiskLevel.SAFE;
import static com.payair.fraud.detection.fixtures.AccountFixtures.randomAccountData;
import static com.payair.fraud.detection.fixtures.TransactionFixtures.randomTransactionData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class FraudDetectionServiceTest {

    private final AccountDataProvider accountDataProvider = Mockito.mock(AccountDataProvider.class);
    private final AssessmentPolicy firstPolicy = Mockito.mock(AssessmentPolicy.class);
    private final AssessmentPolicy secondPolicy = Mockito.mock(AssessmentPolicy.class);

    private final FraudDetectionService fraudDetectionService = new FraudDetectionService(List.of(firstPolicy, secondPolicy), accountDataProvider);

    private final TransactionData transactionData = randomTransactionData();
    private final AccountData accountData = randomAccountData();
    private final Success success = new Success();
    private final Failure failure = new Failure("Failure");

    @BeforeEach
    void setUp() {
        when(accountDataProvider.fetchAccountData(transactionData.bin())).thenReturn(accountData);
    }

    @Test
    public void shouldVerifyTransactionAndReturnSafeResult() {
        when(firstPolicy.assess(transactionData, accountData)).thenReturn(success);
        when(secondPolicy.assess(transactionData, accountData)).thenReturn(success);

        TransactionResult transactionResult = fraudDetectionService.verifyTransaction(transactionData);

        assertEquals(SAFE, transactionResult.riskLevel());
        assertTrue(transactionResult.assessment().isEmpty());
    }

    @Test
    public void shouldReturnLowRiskLevelOnSingleFailure() {
        when(firstPolicy.assess(transactionData, accountData)).thenReturn(success);
        when(secondPolicy.assess(transactionData, accountData)).thenReturn(failure);

        TransactionResult transactionResult = fraudDetectionService.verifyTransaction(transactionData);

        assertEquals(LOW, transactionResult.riskLevel());
        assertEquals(List.of(failure.getMessage()), transactionResult.assessment());
    }

    @Test
    public void shouldReturnMediumRiskLevelOnMultipleFailures() {
        when(firstPolicy.assess(transactionData, accountData)).thenReturn(failure);
        when(secondPolicy.assess(transactionData, accountData)).thenReturn(failure);

        TransactionResult transactionResult = fraudDetectionService.verifyTransaction(transactionData);

        assertEquals(MEDIUM, transactionResult.riskLevel());
        assertEquals(List.of(failure.getMessage(), failure.getMessage()), transactionResult.assessment());
    }

}