package com.payair.fraud.detection.application.data;

import com.payair.fraud.detection.domain.transaction.BIN;
import com.payair.fraud.detection.domain.transaction.ISONumericCountryCode;
import com.payair.fraud.detection.domain.transaction.TransactionAmount;

public record TransactionData(BIN BIN, ISONumericCountryCode country, TransactionAmount amount) {
}
