package com.payair.fraud.detection.infrastructure.http.client;

import com.payair.fraud.detection.infrastructure.exceptions.HttpException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;
import org.jboss.logging.Logger;

@ApplicationScoped
public class HttpErrorMapper implements ResponseExceptionMapper<HttpException> {

    private static final Logger log = Logger.getLogger(HttpErrorMapper.class);

    @Override
    public HttpException toThrowable(Response response) {
        log.errorf("Error occurred on HTTP call: statusCode=%d, response=%s", response.getStatus(), response.readEntity(String.class));
        return new HttpException("Error occurred on HTTP call. Response: " + response.readEntity(String.class), null);
    }
}
