package com.payair.fraud.detection.application;

import com.payair.fraud.detection.domain.data.account.AccountData;
import com.payair.fraud.detection.domain.data.transaction.BIN;

public interface AccountDataProvider {
    AccountData fetchAccountData(BIN bin);
}
