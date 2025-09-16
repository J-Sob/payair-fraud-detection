package com.payair.fraud.detection.domain.exceptions;

import com.payair.fraud.detection.domain.transaction.BIN;

public final class AccountDataNotAvailable extends DomainException {
    public AccountDataNotAvailable(BIN bin) {
        super("Could not find account data for bin: " + bin);
    }
}
