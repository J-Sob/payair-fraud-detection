package com.payair.fraud.detection.profiles;

import io.quarkus.test.junit.QuarkusTestProfile;

import java.util.Map;

public class TestPropertiesProfile implements QuarkusTestProfile {
    @Override
    public Map<String, String> getConfigOverrides() {
        return Map.of(
                "quarkus.rest-client.lookup-api.url", "http://localhost:8080",
                "smallrye.jwt.sign.key.location", "keys/privateKey.pem"
        );
    }
}
