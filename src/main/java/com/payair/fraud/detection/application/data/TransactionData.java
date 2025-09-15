package com.payair.fraud.detection.application.data;

import com.payair.fraud.detection.domain.data.transaction.BIN;
import com.payair.fraud.detection.domain.data.shared.ISONumericCountryCode;
import com.payair.fraud.detection.domain.data.transaction.TransactionAmount;

public record TransactionData(BIN BIN, ISONumericCountryCode country, TransactionAmount amount) {
}
