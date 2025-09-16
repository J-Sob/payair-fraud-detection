package com.payair.fraud.detection.domain.transaction;

import com.payair.fraud.detection.domain.exceptions.WrongDataFormatException;

public record BIN(String value) {
    public BIN {
        if (!value.matches("^[0-9]{6,8}$")) {
            throw new WrongDataFormatException("Invalid bin value: " + value);
        }
    }
}
