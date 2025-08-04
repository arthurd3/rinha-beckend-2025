package com.arthur.rinhabeck.controllers;

import com.arthur.rinhabeck.dtos.PaymentRequest;
import com.arthur.rinhabeck.dtos.PaymentSummaryResponse;
import com.arthur.rinhabeck.usecases.PaymentServiceSimple;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RestController
public class PaymentController {

    private final PaymentServiceSimple paymentService;

    public PaymentController(PaymentServiceSimple paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "OK");
    }

    @PostMapping("/payments")
    public void payment(@RequestBody PaymentRequest paymentRequest) {
        paymentService.processPayment(paymentRequest);
    }

    @GetMapping("/payments-summary")
    public PaymentSummaryResponse paymentsSummary(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to) {
        return paymentService.getPaymentsSummary(from, to);
    }
}
