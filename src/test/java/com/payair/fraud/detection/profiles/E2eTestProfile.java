package com.payair.fraud.detection.profiles;

import io.quarkus.test.junit.QuarkusTestProfile;

import java.util.Map;

public class E2eTestProfile implements QuarkusTestProfile {
    @Override
    public Map<String, String> getConfigOverrides() {
        return Map.of(
                "smallrye.jwt.sign.key.location", "keys/privateKey.pem"
        );
    }

    @Override
    public String getConfigProfile() {
        return "mastercard";
    }
}
