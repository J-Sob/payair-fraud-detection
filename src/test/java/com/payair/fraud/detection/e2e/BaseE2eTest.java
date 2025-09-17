package com.payair.fraud.detection.e2e;

import com.payair.fraud.detection.e2e.user.User;

abstract public class BaseE2eTest {

    protected User givenUser() {
        return new User();
    }
}
