package com.arthur.rinhabeck.dtos;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ProcessorPaymentRequest (
    String correlationId,
    BigDecimal amount,
    Instant requestedAt
) {}

