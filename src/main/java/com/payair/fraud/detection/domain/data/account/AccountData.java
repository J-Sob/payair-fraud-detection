package com.payair.fraud.detection.domain.data.account;

import com.payair.fraud.detection.domain.data.shared.ISONumericCountryCode;

public record AccountData(
        ISONumericCountryCode countryCode,
        FundingSource fundingSource,
        ConsumerType consumerType,
        boolean gamblingBlockEnabled
) {
}
