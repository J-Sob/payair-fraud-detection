package com.payair.fraud.detection.domain.exceptions;

public final class WrongDataFormatException extends DomainException {
    public WrongDataFormatException(String message) {
        super(message);
    }
}
