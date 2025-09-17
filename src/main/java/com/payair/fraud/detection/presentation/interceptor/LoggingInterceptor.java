package com.payair.fraud.detection.presentation.interceptor;

import jakarta.annotation.Priority;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

@Provider
@Priority(0)
public class LoggingInterceptor implements ContainerRequestFilter, ContainerResponseFilter {

    private static final Logger LOG = Logger.getLogger(LoggingInterceptor.class);

    @Override
    public void filter(ContainerRequestContext requestContext) {
        LOG.infof("Incoming request: method=%s, URI=%s, headers=%s",
                requestContext.getMethod(),
                requestContext.getUriInfo().getRequestUri(),
                requestContext.getHeaders());
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        LOG.infof("Outgoing response: statusCode=%d, body=%s headers=%s",
                responseContext.getStatus(),
                responseContext.getEntity(),
                responseContext.getHeaders());
    }
}
