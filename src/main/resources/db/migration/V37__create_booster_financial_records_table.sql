CREATE TABLE booster_financial_records
(
    id           UUID PRIMARY KEY,
    order_id     UUID,
    booster_id   UUID                     NOT NULL,
    record_type  VARCHAR(20)              NOT NULL,
    amount       NUMERIC(19, 4)           NOT NULL,
    status       VARCHAR(20)              NOT NULL,
    created_at   TIMESTAMP WITH TIME ZONE NOT NULL,
    completed_at TIMESTAMP WITH TIME ZONE,
    calculated   BOOLEAN                  NOT NULL DEFAULT false
);

ALTER TABLE booster_financial_records
    ADD CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE SET NULL;

ALTER TABLE booster_financial_records
    ADD CONSTRAINT fk_booster FOREIGN KEY (booster_id) REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE booster_financial_records
    ADD CONSTRAINT valid_amount CHECK (amount <> 0);

ALTER TABLE booster_financial_records
    ADD CONSTRAINT valid_completion_date CHECK (
        (status = 'COMPLETED' AND completed_at IS NOT NULL) OR
        (status <> 'COMPLETED' AND completed_at IS NULL)
        );

CREATE INDEX idx_booster_status_calculated ON booster_financial_records (booster_id, status, calculated);
CREATE INDEX idx_completed_calculated ON booster_financial_records (status, calculated, completed_at);