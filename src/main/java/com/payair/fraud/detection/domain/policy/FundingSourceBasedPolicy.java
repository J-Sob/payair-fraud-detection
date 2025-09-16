package com.payair.fraud.detection.domain.policy;

import com.payair.fraud.detection.application.data.TransactionData;
import com.payair.fraud.detection.domain.account.AccountData;
import com.payair.fraud.detection.domain.policy.result.AssessmentResult;
import com.payair.fraud.detection.domain.policy.result.Failure;
import com.payair.fraud.detection.domain.policy.result.Success;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FundingSourceBasedPolicy implements AssessmentPolicy {

    private static final String assessmentMessage = "Funding source can only by debit or credit.";

    @Override
    public AssessmentResult assess(TransactionData transactionData, AccountData accountData) {
        return switch (accountData.fundingSource()) {
            case DEBIT, CREDIT -> new Success();
            case PREPAID, NONE -> new Failure(assessmentMessage);
        };
    }
}
