package com.payair.fraud.detection.domain.transaction;

import com.payair.fraud.detection.domain.exceptions.WrongDataFormatException;

public record ISONumericCountryCode(String value) {
    public ISONumericCountryCode {
        if(!value.matches("^[0-9]{3}$")) {
            throw new WrongDataFormatException("Invalid country code value: " + value);
        }
    }
}
