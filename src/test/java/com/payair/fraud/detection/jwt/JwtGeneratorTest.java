package com.payair.fraud.detection.jwt;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class JwtGeneratorTest {

    @Test
    public void shouldGenerateJwt() {
        String token = JwtGenerator.generateJwt();
        System.out.println(token);
    }
}
