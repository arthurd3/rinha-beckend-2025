package com.arthur.rinhabeck.usecases;

import com.arthur.rinhabeck.dtos.HealthStatus;
import com.arthur.rinhabeck.dtos.PaymentRequest;
import com.arthur.rinhabeck.dtos.PaymentSummaryResponse;
import com.arthur.rinhabeck.dtos.ProcessorPaymentRequest;
import com.arthur.rinhabeck.repositories.PaymentRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.Instant;

@Service
public class PaymentService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final PaymentRepository paymentRepository;
    private final RestClient restClient;

    public PaymentService(RedisTemplate<String, Object> redisTemplate, PaymentRepository paymentRepository) {
        this.redisTemplate = redisTemplate;
        this.paymentRepository = paymentRepository;
        this.restClient = RestClient.create();
    }

    public void processPayment(PaymentRequest request) {
        HealthStatus defaultStatus = (HealthStatus) redisTemplate.opsForValue().get(ProcessorHealthService.DEFAULT_HEALTH_KEY);
        HealthStatus fallbackStatus = (HealthStatus) redisTemplate.opsForValue().get(ProcessorHealthService.FALLBACK_HEALTH_KEY);

        String chosenProcessorUrl;
        String chosenProcessorType;

        if (defaultStatus != null && !defaultStatus.failing()) {
            chosenProcessorUrl = "http://payment-processor-default:8080";
            chosenProcessorType = "default";
        } else if (fallbackStatus != null && !fallbackStatus.failing()) {
            chosenProcessorUrl = "http://payment-processor-fallback:8080";
            chosenProcessorType = "fallback";
        } else {
            throw new RuntimeException("No payment processors available.");
        }

        var processorRequest = new ProcessorPaymentRequest(request.correlationId(), request.amount(), Instant.now());

        try {
            restClient.post()
                    .uri(chosenProcessorUrl + "/payments")
                    .body(processorRequest)
                    .retrieve()
                    .toBodilessEntity();

            paymentRepository.save(processorRequest, chosenProcessorType);

        } catch(Exception e) {
            throw new RuntimeException("Payment processing failed", e);
        }
    }

    public PaymentSummaryResponse getPaymentsSummary(Instant from, Instant to) {
        var defaultSummary = paymentRepository.getSummaryByProcessorType("default", from, to);
        var fallbackSummary = paymentRepository.getSummaryByProcessorType("fallback", from, to);
        
        return new PaymentSummaryResponse(defaultSummary, fallbackSummary);
    }
}