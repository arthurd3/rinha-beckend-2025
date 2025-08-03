package com.arthur.rinhabeck.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record HealthStatus(
        boolean failing,
        int minResponseTime
) {}
