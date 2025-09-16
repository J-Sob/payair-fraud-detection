package com.payair.fraud.detection.presentation.interceptor;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logmanager.MDC;

import java.util.UUID;

@Provider
public class RequestIdInterceptor implements ContainerRequestFilter, ContainerResponseFilter {

    private static final String REQUEST_ID_HEADER = "X-REQUESTID";
    private static final String REQUEST_ID_MDC = "requestId";


    @Override
    public void filter(ContainerRequestContext requestContext) {
        String requestId = requestContext.getHeaderString(REQUEST_ID_HEADER);

        if (requestId == null) {
            requestId = UUID.randomUUID().toString();
        }

        requestContext.setProperty(REQUEST_ID_HEADER, requestId);
        MDC.put(REQUEST_ID_MDC, requestId);
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        String requestId = ((String) requestContext.getProperty(REQUEST_ID_HEADER));
        responseContext.getHeaders().add(REQUEST_ID_HEADER, requestId);
        MDC.remove(REQUEST_ID_MDC);
    }
}
