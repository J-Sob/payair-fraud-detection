package com.payair.fraud.detection.infrastructure.http.dto;

public record AccountLookupResponse(
        boolean gamblingBlockEnabled,
        Country country,
        String consumerType,
        String fundingSource
) {
    public record Country(
            int code,
            String alpha3,
            String name
    ) {
    }
}
