package com.payair.fraud.detection.domain.policy;

import com.payair.fraud.detection.application.data.TransactionData;
import com.payair.fraud.detection.domain.data.account.AccountData;
import com.payair.fraud.detection.domain.policy.result.AssessmentResult;

public interface AssessmentPolicy {
    AssessmentResult assess(TransactionData transactionData, AccountData accountData);
}
