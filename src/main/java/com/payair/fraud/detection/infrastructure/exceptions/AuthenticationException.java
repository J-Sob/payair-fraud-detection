package com.payair.fraud.detection.infrastructure.exceptions;

public final class AuthenticationException extends InfrastructureException {
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
