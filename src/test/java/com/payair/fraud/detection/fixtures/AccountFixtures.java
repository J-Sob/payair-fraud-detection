package com.payair.fraud.detection.fixtures;

import com.payair.fraud.detection.domain.data.account.AccountData;
import com.payair.fraud.detection.domain.data.account.ConsumerType;
import com.payair.fraud.detection.domain.data.account.FundingSource;
import com.payair.fraud.detection.domain.data.shared.ISONumericCountryCode;
import org.apache.commons.lang3.RandomUtils;

import static com.payair.fraud.detection.fixtures.SharedFixtures.randomCountryCode;

public class AccountFixtures {

    public static ConsumerType randomConsumerType() {
        return ConsumerType.values()[RandomUtils.secure().randomInt(0, ConsumerType.values().length)];
    }

    public static FundingSource randomFundingSource() {
        return FundingSource.values()[RandomUtils.secure().randomInt(0, FundingSource.values().length)];
    }

    public static AccountData accountDataWithCountryCode(ISONumericCountryCode countryCode) {
        return new AccountData(countryCode, randomFundingSource(), randomConsumerType(), RandomUtils.secure().randomBoolean());
    }

    public static AccountData accountDataWithConsumerType(ConsumerType consumerType) {
        return new AccountData(randomCountryCode(), randomFundingSource(), consumerType, RandomUtils.secure().randomBoolean());
    }

    public static AccountData accountDataWithFundingSource(FundingSource fundingSource) {
        return new AccountData(randomCountryCode(), fundingSource, randomConsumerType(), RandomUtils.secure().randomBoolean());
    }

    public static AccountData accountDataWithGamblingBlock(boolean gamblingBlock) {
        return new AccountData(randomCountryCode(), randomFundingSource(), randomConsumerType(), gamblingBlock);
    }
}
