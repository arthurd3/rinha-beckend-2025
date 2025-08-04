package com.arthur.rinhabeck.usecases;

import com.arthur.rinhabeck.dtos.PaymentRequest;
import com.arthur.rinhabeck.dtos.PaymentSummaryResponse;
import com.arthur.rinhabeck.dtos.ProcessorPaymentRequest;
import com.arthur.rinhabeck.repositories.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PaymentServiceSimple {

    private final PaymentRepository paymentRepository;

    public PaymentServiceSimple(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public void processPayment(PaymentRequest request) {
        // Simplified: always use default processor for now
        String chosenProcessorType = "default";
        
        var processorRequest = new ProcessorPaymentRequest(request.correlationId(), request.amount(), Instant.now());

        try {
            // Simulate successful payment processing
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
