package com.arthur.rinhabeck.controllers;

import com.arthur.rinhabeck.dtos.PaymentRequest;
import com.arthur.rinhabeck.dtos.PaymentSummaryResponse;
import com.arthur.rinhabeck.usecases.PaymentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public void payment(@RequestBody PaymentRequest paymentRequest) {
        paymentService.processPayment(paymentRequest);
    }

    @GetMapping("-summary")
    public PaymentSummaryResponse paymentsSummary(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to) {
        return paymentService.getPaymentsSummary(from, to);
    }
}
