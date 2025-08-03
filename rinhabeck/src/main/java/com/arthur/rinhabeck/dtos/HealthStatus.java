package com.arthur.rinhabeck.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;

@RegisterReflectionForBinding
public record HealthStatus(
        @JsonProperty("failing") boolean failing,
        @JsonProperty("minResponseTime") int minResponseTime
) {}
