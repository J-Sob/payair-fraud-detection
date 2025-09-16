package com.payair.fraud.detection.application.data;

import com.payair.fraud.detection.domain.shared.ISONumericCountryCode;
import com.payair.fraud.detection.domain.transaction.BIN;
import com.payair.fraud.detection.domain.transaction.TransactionAmount;

public record TransactionData(BIN bin, ISONumericCountryCode country, TransactionAmount amount) {
}
