package com.payair.fraud.detection.infrastructure.http.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastercard.developer.oauth.OAuth;
import com.mastercard.developer.utils.AuthenticationUtils;
import com.payair.fraud.detection.infrastructure.exceptions.HttpException;
import com.payair.fraud.detection.infrastructure.exceptions.KeystoreException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;

@Provider
@ApplicationScoped
public class OAuth1RequestFilter implements ClientRequestFilter {

    private final String consumerKey;
    private final PrivateKey signingKey;
    private final ObjectMapper objectMapper;

    @Inject
    public OAuth1RequestFilter(
            @ConfigProperty(name = "mastercard.consumer.key") String consumerKey,
            @ConfigProperty(name = "mastercard.keystore.path") String keystorePath,
            @ConfigProperty(name = "mastercard.keystore.password") String keystorePassword,
            @ConfigProperty(name = "mastercard.keystore.alias") String keyAlias,
            ObjectMapper objectMapper) {
        try {
            this.consumerKey = consumerKey;
            this.signingKey = AuthenticationUtils.loadSigningKey(keystorePath, keyAlias, keystorePassword);
            this.objectMapper = objectMapper;
        } catch (Exception e) {
            throw new KeystoreException("Unable to load keystore", e);
        }
    }

    @Override
    public void filter(ClientRequestContext requestContext) {
        try {
            String payload = payload(requestContext);

            String authHeader = OAuth.getAuthorizationHeader(requestContext.getUri(),
                    requestContext.getMethod(),
                    payload,
                    StandardCharsets.UTF_8,
                    consumerKey,
                    signingKey);

            requestContext.getHeaders().add("Authorization", authHeader);
        } catch (Exception e) {
            throw new HttpException("Unable to process authorization", e);
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
