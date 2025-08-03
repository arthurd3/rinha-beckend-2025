package com.arthur.rinhabeck.repositories;

import com.arthur.rinhabeck.dtos.ProcessorPaymentRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String INSERT_PAYMENT_SQL = """
        INSERT INTO payments (correlationId, amount, requested_at, processor_type)
        VALUES (?, ?, ?, ?)
    """;

    public PaymentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(ProcessorPaymentRequest request, String processorType) {
        jdbcTemplate.update(INSERT_PAYMENT_SQL,
                request.correlationId(),
                request.amount(),
                request.requestedAt(),
                processorType);
    }
}