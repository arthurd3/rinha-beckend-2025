CREATE TABLE IF NOT EXISTS payments (
    id SERIAL PRIMARY KEY,
    payment_id VARCHAR(255) NOT NULL,
    amount INTEGER NOT NULL,
    type VARCHAR(255) NOT NULL,
    description TEXT,
    processed_amount INTEGER NOT NULL,
    processor_type VARCHAR(20) NOT NULL DEFAULT 'default'
);

CREATE INDEX IF NOT EXISTS idx_payments_processor_type ON payments(processor_type);
CREATE INDEX IF NOT EXISTS idx_payments_payment_id ON payments(payment_id);