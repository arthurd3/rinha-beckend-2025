package com.arthur.rinhabeck.dtos.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentRequest(
        @NotNull UUID correlationId,
        @NotNull BigDecimal amount
) {}

