package com.payair.fraud.detection.presentation;

import com.payair.fraud.detection.domain.exceptions.AccountDataNotAvailable;
import com.payair.fraud.detection.domain.exceptions.DomainException;
import com.payair.fraud.detection.domain.exceptions.WrongDataFormatException;
import com.payair.fraud.detection.infrastructure.exceptions.HttpException;
import com.payair.fraud.detection.infrastructure.exceptions.InfrastructureException;
import com.payair.fraud.detection.infrastructure.exceptions.KeystoreException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;
import static jakarta.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;


@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {

    private static final Logger log = Logger.getLogger(ExceptionHandler.class);

    @Override
    public Response toResponse(Exception exception) {
        log.error("Handling thrown exception", exception);

        return switch (exception) {
            case DomainException domainException -> handleDomainException(domainException);
            case InfrastructureException infrastructureException ->
                    handleInfrastructureException(infrastructureException);
            default -> buildResponse(INTERNAL_SERVER_ERROR, "Unexpected error occurred");
        };
    }

    private Response handleDomainException(DomainException exception) {
        return switch (exception) {
            case AccountDataNotAvailable accountDataNotAvailable ->
                    buildResponse(NOT_FOUND, accountDataNotAvailable.getMessage());
            case WrongDataFormatException wrongDataFormatException ->
                    buildResponse(BAD_REQUEST, wrongDataFormatException.getMessage());
        };
    }

    private Response handleInfrastructureException(InfrastructureException exception) {
        return switch (exception) {
            case HttpException httpException ->
                    buildResponse(Response.Status.SERVICE_UNAVAILABLE, httpException.getMessage());
            case KeystoreException keystoreException ->
                    buildResponse(INTERNAL_SERVER_ERROR, keystoreException.getMessage());
        };
    }

    private Response buildResponse(Response.Status status, String errorMessage) {
        return Response.status(status)
                .entity(new ErrorResponse(errorMessage))
                .build();
    }

    public record ErrorResponse(String message) {
    }
}
