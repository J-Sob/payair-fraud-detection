package com.payair.fraud.detection.domain.policy;

import com.payair.fraud.detection.application.data.TransactionData;
import com.payair.fraud.detection.domain.data.account.AccountData;
import com.payair.fraud.detection.domain.policy.result.AssessmentResult;
import com.payair.fraud.detection.domain.policy.result.Failure;
import com.payair.fraud.detection.domain.policy.result.Success;
import org.junit.jupiter.api.Test;

import static com.payair.fraud.detection.fixtures.AccountFixtures.accountDataWithGamblingBlock;
import static com.payair.fraud.detection.fixtures.TransactionFixtures.randomTransactionData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class GamblingBlockBasedPolicyTest {

    private final TransactionData transactionData = randomTransactionData();

    private final GamblingBlockBasedPolicy policy = new GamblingBlockBasedPolicy();

    @Test
    void shouldReturnSuccess() {
        AccountData accountData = accountDataWithGamblingBlock(false);

        AssessmentResult assessment = policy.assess(transactionData, accountData);

        assertInstanceOf(Success.class, assessment);
    }

    @Test
    void shouldReturnFailure() {
        AccountData accountData = accountDataWithGamblingBlock(true);

        AssessmentResult assessment = policy.assess(transactionData, accountData);

        assertInstanceOf(Failure.class, assessment);
        assertEquals("Account has an active gambling block.", ((Failure) assessment).getMessage());
    }
}