package com.payair.fraud.detection.infrastructure.http.client.auth;

import jakarta.ws.rs.client.ClientRequestContext;

public interface OAuth1HeaderFactory {
    String createHeader(ClientRequestContext requestContext);
}
