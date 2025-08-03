package com.arthur.rinhabeck.dtos;

import java.math.BigDecimal;

public record PaymentRequest(
         String correlationId,
         BigDecimal amount
) {}

