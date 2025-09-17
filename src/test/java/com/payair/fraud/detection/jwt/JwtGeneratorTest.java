package com.payair.fraud.detection.jwt;

import com.payair.fraud.detection.profiles.TestPropertiesProfile;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import org.junit.jupiter.api.Test;

@QuarkusTest
@TestProfile(TestPropertiesProfile.class)
public class JwtGeneratorTest {

    @Test
    public void jwtGenerationVector() {
        String token = JwtGenerator.generateJwt();
        System.out.println(token);
    }
}
