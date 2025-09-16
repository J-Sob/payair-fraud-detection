package com.payair.fraud.detection.infrastructure.http;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.payair.fraud.detection.domain.account.AccountData;
import com.payair.fraud.detection.domain.exceptions.AccountDataNotAvailable;
import com.payair.fraud.detection.domain.shared.ISONumericCountryCode;
import com.payair.fraud.detection.domain.transaction.BIN;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.payair.fraud.detection.domain.account.ConsumerType.CONSUMER;
import static com.payair.fraud.detection.domain.account.FundingSource.DEBIT;
import static com.payair.fraud.detection.fixtures.TransactionFixtures.randomBin;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class HttpAccountDataProviderTest {

    private static WireMockServer wireMock;

    @Inject
    HttpAccountDataProvider accountDataProvider;

    @BeforeAll
    static void setup() {
        wireMock = new WireMockServer(8080);
        wireMock.start();
    }

    @AfterAll
    static void tearDown() {
        wireMock.stop();
    }

    @Test
    public void shouldSuccessfullyFetchAccountData() {
        BIN bin = randomBin();
        AccountData expected = new AccountData(new ISONumericCountryCode("372"), DEBIT, CONSUMER, false);

        AccountData accountData = accountDataProvider.fetchAccountData(bin);

        assertEquals(expected, accountData);
    }

    @Test
    public void shouldThrowExceptionWhenResponseIsEmpty() {
        BIN bin = new BIN("111111");

        Assertions.assertThrows(AccountDataNotAvailable.class, () -> accountDataProvider.fetchAccountData(bin));
    }

}