package com.arthur.rinhabeck.repositories;

import com.arthur.rinhabeck.dtos.PaymentSummaryResponse;
import com.arthur.rinhabeck.dtos.ProcessorPaymentRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;

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

    public PaymentSummaryResponse.SummaryDetails getSummaryByProcessorType(String processorType, Instant from, Instant to) {
        String sql = """
            SELECT COUNT(*) as totalRequests, COALESCE(SUM(amount), 0) as totalAmount
            FROM payments
            WHERE processor_type = ?
        """;
        
        Object[] params;
        if (from != null && to != null) {
            sql += " AND requested_at BETWEEN ? AND ?";
            params = new Object[]{processorType, from, to};
        } else if (from != null) {
            sql += " AND requested_at >= ?";
            params = new Object[]{processorType, from};
        } else if (to != null) {
            sql += " AND requested_at <= ?";
            params = new Object[]{processorType, to};
        } else {
            params = new Object[]{processorType};
        }

        return jdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> 
            new PaymentSummaryResponse.SummaryDetails(
                rs.getLong("totalRequests"),
                rs.getBigDecimal("totalAmount")
            )
        );
    }
}