package com.payair.fraud.detection.domain.account;

import com.payair.fraud.detection.domain.shared.ISONumericCountryCode;

public record AccountData(
        ISONumericCountryCode countryCode,
        FundingSource fundingSource,
        ConsumerType consumerType,
        boolean gamblingBlockEnabled
) {
}
