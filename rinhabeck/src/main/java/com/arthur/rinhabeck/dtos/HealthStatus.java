package com.arthur.rinhabeck.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;

@RegisterReflectionForBinding
public class HealthStatus {
    
    @JsonProperty("failing")
    private boolean failing;
    
    @JsonProperty("minResponseTime")
    private int minResponseTime;
    
    public HealthStatus() {
    }
    
    public HealthStatus(boolean failing, int minResponseTime) {
        this.failing = failing;
        this.minResponseTime = minResponseTime;
    }
    
    public boolean failing() {
        return failing;
    }
    
    public int minResponseTime() {
        return minResponseTime;
    }
    
    public void setFailing(boolean failing) {
        this.failing = failing;
    }
    
    public void setMinResponseTime(int minResponseTime) {
        this.minResponseTime = minResponseTime;
    }
}
