package com.payair.fraud.detection.fixtures;

import com.payair.fraud.detection.application.data.TransactionData;
import com.payair.fraud.detection.application.data.TransactionResult;
import com.payair.fraud.detection.domain.risk.RiskLevel;
import com.payair.fraud.detection.domain.shared.ISONumericCountryCode;
import com.payair.fraud.detection.domain.transaction.BIN;
import com.payair.fraud.detection.domain.transaction.TransactionAmount;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;

import static com.payair.fraud.detection.domain.risk.RiskLevel.SAFE;
import static com.payair.fraud.detection.fixtures.SharedFixtures.randomCountryCode;
import static java.util.Collections.emptyList;

public class TransactionFixtures {

    public static BIN randomBin() {
        return new BIN(RandomStringUtils.secure().nextNumeric(8));
    }

    public static TransactionAmount randomTransactionAmount() {
        return new TransactionAmount(RandomUtils.secure().randomDouble());
    }

    public static TransactionData randomTransactionData() {
        return new TransactionData(randomBin(), randomCountryCode(), randomTransactionAmount());
    }

    public static TransactionData transactionDataWithCountryCode(ISONumericCountryCode countryCode) {
        return new TransactionData(randomBin(), countryCode, randomTransactionAmount());
    }

    public static TransactionResult safeTransactionResult() {
        return new TransactionResult(SAFE, emptyList());
    }

    public static TransactionResult transactionResult(RiskLevel riskLevel, String... assessment) {
        return new TransactionResult(riskLevel, List.of(assessment));
    }
}
