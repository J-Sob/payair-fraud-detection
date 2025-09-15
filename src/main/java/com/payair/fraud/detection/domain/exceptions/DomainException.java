package com.payair.fraud.detection.domain.exceptions;

public sealed class DomainException extends RuntimeException permits WrongDataFormatException {
    public DomainException(String message) {
        super(message);
    }
}
