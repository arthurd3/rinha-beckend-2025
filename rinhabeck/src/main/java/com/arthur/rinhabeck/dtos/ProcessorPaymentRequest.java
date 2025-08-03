package com.arthur.rinhabeck.dtos;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;

@RegisterReflectionForBinding
public record ProcessorPaymentRequest (
    @JsonProperty("correlationId") String correlationId,
    @JsonProperty("amount") BigDecimal amount,
    @JsonProperty("requestedAt") Instant requestedAt
) {}

