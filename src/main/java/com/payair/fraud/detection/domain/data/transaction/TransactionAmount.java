package com.payair.fraud.detection.domain.data.transaction;

import com.payair.fraud.detection.domain.exceptions.WrongDataFormatException;

public record TransactionAmount(double value) {
    public TransactionAmount {
        if (value < 0) {
            throw new WrongDataFormatException("Amount value cannot be negative: " + value);
        }
    }
}
