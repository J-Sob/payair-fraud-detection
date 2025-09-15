package com.payair.fraud.detection.domain.policy;

import com.payair.fraud.detection.application.data.TransactionData;
import com.payair.fraud.detection.domain.data.account.AccountData;
import com.payair.fraud.detection.domain.policy.result.AssessmentResult;
import com.payair.fraud.detection.domain.policy.result.Failure;
import com.payair.fraud.detection.domain.policy.result.Success;

public class ConsumerTypeBasedPolicy implements AssessmentPolicy {

    private static final String assessmentMessage = "Account is not of consumer type.";

    @Override
    public AssessmentResult assess(TransactionData transactionData, AccountData accountData) {
        return switch (accountData.consumerType()){
            case CONSUMER -> new Success();
            case CORPORATE, NONE -> new Failure(assessmentMessage);
        };
    }
}
