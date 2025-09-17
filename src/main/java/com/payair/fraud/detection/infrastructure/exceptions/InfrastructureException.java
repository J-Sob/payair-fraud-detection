package com.payair.fraud.detection.infrastructure.exceptions;

public abstract sealed class InfrastructureException extends RuntimeException permits AuthenticationException, HttpException, KeystoreException {
    public InfrastructureException(String message, Throwable cause) {
        super(message, cause);
    }
}
