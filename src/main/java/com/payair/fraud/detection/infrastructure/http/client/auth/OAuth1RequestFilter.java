package com.payair.fraud.detection.infrastructure.http.client.auth;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;

@ApplicationScoped
public class OAuth1RequestFilter implements ClientRequestFilter {

    private final OAuth1HeaderFactory oauth1HeaderFactory;

    @Inject
    public OAuth1RequestFilter(OAuth1HeaderFactory oauth1HeaderFactory) {
        this.oauth1HeaderFactory = oauth1HeaderFactory;
    }

    @Override
    public void filter(ClientRequestContext requestContext) {
        String authorizationHeader = oauth1HeaderFactory.createHeader(requestContext);
        requestContext.getHeaders().add("Authorization", authorizationHeader);
    }

}
