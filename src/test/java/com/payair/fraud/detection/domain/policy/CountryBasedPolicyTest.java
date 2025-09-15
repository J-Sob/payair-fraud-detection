package com.payair.fraud.detection.domain.policy;

import com.payair.fraud.detection.application.data.TransactionData;
import com.payair.fraud.detection.domain.data.account.AccountData;
import com.payair.fraud.detection.domain.data.shared.ISONumericCountryCode;
import com.payair.fraud.detection.domain.policy.result.AssessmentResult;
import com.payair.fraud.detection.domain.policy.result.Failure;
import com.payair.fraud.detection.domain.policy.result.Success;
import org.junit.jupiter.api.Test;

import static com.payair.fraud.detection.fixtures.AccountFixtures.accountDataWithCountryCode;
import static com.payair.fraud.detection.fixtures.SharedFixtures.randomCountryCode;
import static com.payair.fraud.detection.fixtures.TransactionFixtures.transactionDataWithCountryCode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class CountryBasedPolicyTest {

    private final ISONumericCountryCode countryCode = randomCountryCode();
    private final ISONumericCountryCode differentCountryCode = randomCountryCode();

    private final CountryBasedPolicy policy = new CountryBasedPolicy();

    @Test
    public void shouldReturnSuccess() {
        TransactionData transactionData = transactionDataWithCountryCode(countryCode);
        AccountData accountData = accountDataWithCountryCode(countryCode);

        AssessmentResult assessment = policy.assess(transactionData, accountData);

        assertInstanceOf(Success.class, assessment);
    }

    @Test
    public void shouldReturnFailure() {
        TransactionData transactionData = transactionDataWithCountryCode(countryCode);
        AccountData accountData = accountDataWithCountryCode(differentCountryCode);

        AssessmentResult assessment = policy.assess(transactionData, accountData);

        assertInstanceOf(Failure.class, assessment);
        assertEquals("Transaction country isn't the same as account country of origin.", ((Failure) assessment).getMessage());
    }
}