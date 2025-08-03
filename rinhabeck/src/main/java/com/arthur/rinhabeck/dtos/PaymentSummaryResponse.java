package com.arthur.rinhabeck.dtos;

import java.math.BigDecimal;

public record PaymentSummaryResponse(
        SummaryDetails defaults,
        SummaryDetails fallback
) {
    public record SummaryDetails(
            long totalRequests,
            BigDecimal totalAmount
    ) {}
}
