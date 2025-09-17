package com.payair.fraud.detection.e2e;

import com.payair.fraud.detection.e2e.transaction.TransactionFlowValidation;
import com.payair.fraud.detection.profiles.E2eTestProfile;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import static com.payair.fraud.detection.domain.risk.RiskLevel.HIGH;
import static org.jboss.resteasy.reactive.RestResponse.StatusCode.UNAUTHORIZED;

@QuarkusTest
@TestProfile(E2eTestProfile.class)
@EnabledIfSystemProperty(named = "e2e", matches = "true")
public class FraudDetectionE2eTest extends BaseE2eTest {

    @Test
    public void shouldSuccessfullyPerformTransactionVerification() {
        TransactionFlowValidation transactionFlowValidation = givenUser().isAuthenticated()
                .sendsValidTransactionRequest();

        transactionFlowValidation.isSuccessful()
                .riskLevelIsEqualTo(HIGH)
                .assessmentContainsExactly(
                        "Funding source can only by debit or credit.",
                        "Account is not of consumer type.",
                        "Transaction country isn't the same as account country of origin."
                );

    }

    @Test
    public void shouldFailToVerifyUnauthorizedTransactionVerification() {
        TransactionFlowValidation transactionFlowValidation = givenUser()
                .sendsValidTransactionRequest();

        transactionFlowValidation.returnedStatusCode(UNAUTHORIZED);
    }
}
