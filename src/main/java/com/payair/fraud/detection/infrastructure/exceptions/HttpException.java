package com.payair.fraud.detection.infrastructure.exceptions;

public final class HttpException extends InfrastructureException {
    public HttpException(String message, Throwable cause) {
        super(message, cause);
    }
}
