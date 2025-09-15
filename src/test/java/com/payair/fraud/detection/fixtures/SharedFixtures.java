package com.payair.fraud.detection.fixtures;

import com.payair.fraud.detection.domain.data.shared.ISONumericCountryCode;
import org.apache.commons.lang3.RandomStringUtils;

public class SharedFixtures {

    public static ISONumericCountryCode randomCountryCode() {
        return new ISONumericCountryCode(RandomStringUtils.secure().nextNumeric(3));
    }

}
