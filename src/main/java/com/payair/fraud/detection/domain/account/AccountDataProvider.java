package com.payair.fraud.detection.domain.account;

import com.payair.fraud.detection.domain.transaction.BIN;

public interface AccountDataProvider {
    AccountData fetchAccountData(BIN bin);
}
