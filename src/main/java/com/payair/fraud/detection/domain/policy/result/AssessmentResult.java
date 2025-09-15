package com.payair.fraud.detection.domain.policy.result;

public abstract sealed class AssessmentResult permits Failure, Success {
}
