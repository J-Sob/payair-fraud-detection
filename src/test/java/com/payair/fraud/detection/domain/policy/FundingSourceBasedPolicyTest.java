package com.payair.fraud.detection.domain.policy;

import com.payair.fraud.detection.application.data.TransactionData;
import com.payair.fraud.detection.domain.account.AccountData;
import com.payair.fraud.detection.domain.account.FundingSource;
import com.payair.fraud.detection.domain.policy.result.AssessmentResult;
import com.payair.fraud.detection.domain.policy.result.Failure;
import com.payair.fraud.detection.domain.policy.result.Success;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static com.payair.fraud.detection.fixtures.AccountFixtures.accountDataWithFundingSource;
import static com.payair.fraud.detection.fixtures.TransactionFixtures.randomTransactionData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class FundingSourceBasedPolicyTest {

    private final TransactionData transactionData = randomTransactionData();

    private final FundingSourceBasedPolicy policy = new FundingSourceBasedPolicy();

    @ParameterizedTest
    @EnumSource(value = FundingSource.class, names = {"CREDIT", "DEBIT"})
    public void shouldReturnSuccess(FundingSource fundingSource) {
        AccountData accountData = accountDataWithFundingSource(fundingSource);

        AssessmentResult assessment = policy.assess(transactionData, accountData);

        assertInstanceOf(Success.class, assessment);
    }

    @ParameterizedTest
    @EnumSource(value = FundingSource.class, names = {"PREPAID", "NONE"})
    public void shouldReturnFailure(FundingSource fundingSource) {
        AccountData accountData = accountDataWithFundingSource(fundingSource);

        AssessmentResult assessment = policy.assess(transactionData, accountData);

        assertInstanceOf(Failure.class, assessment);
        assertEquals("Funding source can only by debit or credit.", ((Failure) assessment).getMessage());
    }
}