package com.payair.fraud.detection.domain.policy;

import com.payair.fraud.detection.application.data.TransactionData;
import com.payair.fraud.detection.domain.account.AccountData;
import com.payair.fraud.detection.domain.policy.result.AssessmentResult;
import com.payair.fraud.detection.domain.policy.result.Failure;
import com.payair.fraud.detection.domain.policy.result.Success;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CountryBasedPolicy implements AssessmentPolicy {

    private static final String assessmentMessage = "Transaction country isn't the same as account country of origin.";

    @Override
    public AssessmentResult assess(TransactionData transactionData, AccountData accountData) {
        if (transactionData.country().equals(accountData.countryCode())) {
            return new Success();
        } else {
            return new Failure(assessmentMessage);
        }
    }
}
