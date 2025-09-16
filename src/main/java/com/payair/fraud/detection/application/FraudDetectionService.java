package com.payair.fraud.detection.application;

import com.payair.fraud.detection.application.data.TransactionData;
import com.payair.fraud.detection.application.data.TransactionResult;
import com.payair.fraud.detection.domain.account.AccountData;
import com.payair.fraud.detection.domain.account.AccountDataProvider;
import com.payair.fraud.detection.domain.policy.AssessmentPolicy;
import com.payair.fraud.detection.domain.policy.result.Failure;
import com.payair.fraud.detection.domain.risk.RiskAssessment;
import io.quarkus.arc.All;
import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class FraudDetectionService {

    private static final Logger log = Logger.getLogger(FraudDetectionService.class);

    private final List<AssessmentPolicy> policies;
    private final AccountDataProvider accountDataProvider;

    @Inject
    public FraudDetectionService(@All List<AssessmentPolicy> policies, AccountDataProvider accountDataProvider) {
        this.policies = policies;
        this.accountDataProvider = accountDataProvider;
    }

    @CacheResult(cacheName = "transaction-verification-cache")
    public TransactionResult verifyTransaction(TransactionData transactionData) {
        log.info("Verifying transaction: " + transactionData);
        RiskAssessment riskAssessment = new RiskAssessment();
        AccountData accountData = accountDataProvider.fetchAccountData(transactionData.bin());

        policies.stream()
                .map(policy -> policy.assess(transactionData, accountData))
                .filter(assessmentResult -> assessmentResult instanceof Failure)
                .forEach(failure -> riskAssessment.increaseRiskLevel(((Failure) failure).getMessage()));

        TransactionResult transactionResult = buildResult(riskAssessment);
        log.info("Transaction verification finished: " + transactionResult);
        return transactionResult;
    }

    private TransactionResult buildResult(RiskAssessment riskAssessment) {
        return new TransactionResult(riskAssessment.getRiskLevel(), riskAssessment.getRiskAssessment());
    }
}
