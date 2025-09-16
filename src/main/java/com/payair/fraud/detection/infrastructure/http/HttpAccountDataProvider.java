package com.payair.fraud.detection.infrastructure.http;

import com.payair.fraud.detection.domain.account.AccountData;
import com.payair.fraud.detection.domain.account.AccountDataProvider;
import com.payair.fraud.detection.domain.account.ConsumerType;
import com.payair.fraud.detection.domain.account.FundingSource;
import com.payair.fraud.detection.domain.exceptions.AccountDataNotAvailable;
import com.payair.fraud.detection.domain.shared.ISONumericCountryCode;
import com.payair.fraud.detection.domain.transaction.BIN;
import com.payair.fraud.detection.infrastructure.http.client.AccountLookupClient;
import com.payair.fraud.detection.infrastructure.http.dto.AccountLookupRequest;
import com.payair.fraud.detection.infrastructure.http.dto.AccountLookupResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;


@ApplicationScoped
public class HttpAccountDataProvider implements AccountDataProvider {

    private static final Logger log = Logger.getLogger(HttpAccountDataProvider.class);

    private final AccountLookupClient client;

    @Inject
    public HttpAccountDataProvider(@RestClient AccountLookupClient client) {
        this.client = client;
    }

    @Override
    public AccountData fetchAccountData(BIN bin) {
        log.info("Fetching account data for bin: " + bin);

        AccountLookupRequest request = new AccountLookupRequest(bin.value());
        AccountLookupResponse accountLookupResponse = client.fetchAccounts(request)
                .stream()
                .findFirst()
                .orElseThrow(() -> new AccountDataNotAvailable(bin));

        log.info("Received account data: " + accountLookupResponse);
        return mapToAccountData(accountLookupResponse);
    }

    private AccountData mapToAccountData(AccountLookupResponse response) {
        return new AccountData(
                new ISONumericCountryCode(String.valueOf(response.country().code())),
                FundingSource.valueOf(response.fundingSource()),
                ConsumerType.valueOf(response.consumerType()),
                response.gamblingBlockEnabled()
        );
    }
}
