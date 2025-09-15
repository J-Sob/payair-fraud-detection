package com.payair.fraud.detection.domain.policy;

import com.payair.fraud.detection.application.data.TransactionData;
import com.payair.fraud.detection.domain.data.account.AccountData;
import com.payair.fraud.detection.domain.policy.result.AssessmentResult;
import com.payair.fraud.detection.domain.policy.result.Failure;
import com.payair.fraud.detection.domain.policy.result.Success;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GamblingBlockBasedPolicy implements AssessmentPolicy {

    private static final String assessmentMessage = "Account has an active gambling block.";

    @Override
    public AssessmentResult assess(TransactionData transactionData, AccountData accountData) {
        if (!accountData.gamblingBlockEnabled()) {
            return new Success();
        } else {
            return new Failure(assessmentMessage);
        }
    }
}
