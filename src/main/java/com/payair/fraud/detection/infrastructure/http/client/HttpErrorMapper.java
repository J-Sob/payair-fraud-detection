package com.payair.fraud.detection.infrastructure.http.client;

import com.payair.fraud.detection.infrastructure.exceptions.HttpException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

@Provider
public class HttpErrorMapper implements ResponseExceptionMapper<HttpException> {
    @Override
    public HttpException toThrowable(Response response) {
        return new HttpException("Error occured on HTTP call. Response: " + response.readEntity(String.class), null);
    }
}
