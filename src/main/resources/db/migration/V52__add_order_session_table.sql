CREATE TABLE order_session
(
    id              BIGSERIAL PRIMARY KEY,
    order_id        UUID                     NOT NULL REFERENCES orders (id),
    user_id         UUID                     NOT NULL REFERENCES users (id),
    duration        INTEGER                  NOT NULL,
    stream_link     TEXT,
    start_date_time TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    end_date_time   TIMESTAMP WITH TIME ZONE,
    status          VARCHAR(20)              NOT NULL DEFAULT 'ACTIVE',
    created_at      TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_order_session_order_id ON order_session (order_id);
CREATE INDEX idx_order_session_user_id ON order_session (user_id);
CREATE INDEX idx_order_session_status ON order_session (status);