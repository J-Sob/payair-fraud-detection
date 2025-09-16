package com.payair.fraud.detection.domain.exceptions;

public abstract sealed class DomainException extends RuntimeException permits WrongDataFormatException, AccountDataNotAvailable {
    public DomainException(String message) {
        super(message);
    }
}
