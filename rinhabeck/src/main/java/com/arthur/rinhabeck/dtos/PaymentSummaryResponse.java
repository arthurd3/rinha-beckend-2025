package com.arthur.rinhabeck.dtos;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;

@RegisterReflectionForBinding
public record PaymentSummaryResponse(
        @JsonProperty("defaults") SummaryDetails defaults,
        @JsonProperty("fallback") SummaryDetails fallback
) {
    @RegisterReflectionForBinding
    public record SummaryDetails(
            @JsonProperty("totalRequests") long totalRequests,
            @JsonProperty("totalAmount") BigDecimal totalAmount
    ) {}
}
