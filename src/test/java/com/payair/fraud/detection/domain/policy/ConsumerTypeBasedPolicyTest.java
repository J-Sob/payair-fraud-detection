package com.payair.fraud.detection.domain.policy;

import com.payair.fraud.detection.application.data.TransactionData;
import com.payair.fraud.detection.domain.account.AccountData;
import com.payair.fraud.detection.domain.account.ConsumerType;
import com.payair.fraud.detection.domain.policy.result.AssessmentResult;
import com.payair.fraud.detection.domain.policy.result.Failure;
import com.payair.fraud.detection.domain.policy.result.Success;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static com.payair.fraud.detection.domain.account.ConsumerType.CONSUMER;
import static com.payair.fraud.detection.fixtures.AccountFixtures.accountDataWithConsumerType;
import static com.payair.fraud.detection.fixtures.TransactionFixtures.randomTransactionData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class ConsumerTypeBasedPolicyTest {

    private final TransactionData transactionData = randomTransactionData();

    private final ConsumerTypeBasedPolicy policy = new ConsumerTypeBasedPolicy();

    @Test
    public void shouldReturnSuccess() {
        AccountData accountData = accountDataWithConsumerType(CONSUMER);

        AssessmentResult assessment = policy.assess(transactionData, accountData);

        assertInstanceOf(Success.class, assessment);
    }

    @ParameterizedTest
    @EnumSource(value = ConsumerType.class, names = {"CORPORATE", "NONE"})
    public void shouldReturnFailure(ConsumerType consumerType) {
        AccountData accountData = accountDataWithConsumerType(consumerType);

        AssessmentResult assessment = policy.assess(transactionData, accountData);

        assertInstanceOf(Failure.class, assessment);
        assertEquals("Account is not of consumer type.", ((Failure) assessment).getMessage());
    }
}