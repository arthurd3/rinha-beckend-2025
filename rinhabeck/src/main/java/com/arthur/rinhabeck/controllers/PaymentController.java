package com.arthur.rinhabeck.controllers;

import com.arthur.rinhabeck.dtos.PaymentRequest;
import com.arthur.rinhabeck.usecases.PaymentService;
import org.springframework.web.bind.annotation.*;

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

}
