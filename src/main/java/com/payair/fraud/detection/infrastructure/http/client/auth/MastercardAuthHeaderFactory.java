package com.payair.fraud.detection.infrastructure.http.client.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastercard.developer.oauth.OAuth;
import com.mastercard.developer.utils.AuthenticationUtils;
import com.payair.fraud.detection.infrastructure.exceptions.AuthenticationException;
import com.payair.fraud.detection.infrastructure.exceptions.KeystoreException;
import jakarta.ws.rs.client.ClientRequestContext;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;

public class MastercardAuthHeaderFactory implements OAuth1HeaderFactory {

    private final String consumerKey;
    private final PrivateKey signingKey;
    private final ObjectMapper objectMapper;

    public MastercardAuthHeaderFactory(String consumerKey, String keystorePath, String keystorePassword, String keyAlias, ObjectMapper objectMapper) {
        try {
            this.consumerKey = consumerKey;
            this.signingKey = AuthenticationUtils.loadSigningKey(keystorePath, keyAlias, keystorePassword);
            this.objectMapper = objectMapper;
        } catch (Exception e) {
            throw new KeystoreException("Unable to load keystore", e);
        }
    }

    @Override
    public String createHeader(ClientRequestContext requestContext) {
        try {
            String payload = payload(requestContext);

            return OAuth.getAuthorizationHeader(requestContext.getUri(),
                    requestContext.getMethod(),
                    payload,
                    StandardCharsets.UTF_8,
                    consumerKey,
                    signingKey);
        } catch (Exception e) {
            throw new AuthenticationException("Unable to create authentication header", e);
        }
    }

    private String payload(ClientRequestContext requestContext) throws JsonProcessingException {
        if (requestContext.getEntity() != null) {
            return objectMapper.writeValueAsString(requestContext.getEntity());
        } else {
            return "";
        }
    }
}
