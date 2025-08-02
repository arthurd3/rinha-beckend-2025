CREATE UNLOGGED TABLE payments (
    correlationId UUID PRIMARY KEY,
    amount DECIMAL NOT NULL,
    requested_at TIMESTAMP NOT NULL,
    processor_type VARCHAR(10) NOT NULL
);

CREATE INDEX idx_payments_requested_at_processor ON payments (requested_at, processor_type);