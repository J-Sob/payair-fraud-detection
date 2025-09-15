package com.payair.fraud.detection.domain.policy.result;

public final class Failure extends AssessmentResult {

    private final String message;

    public Failure(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
