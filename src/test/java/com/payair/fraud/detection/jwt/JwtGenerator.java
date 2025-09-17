package com.payair.fraud.detection.jwt;

import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.jwt.Claims;

public class JwtGenerator {

    public static String generateJwt() {
        return Jwt.issuer("https://example.com/issuer")
                .groups("User")
                .upn("test")
                .claim(Claims.email.name(), "test@test.com")
                .sign();
    }

}
