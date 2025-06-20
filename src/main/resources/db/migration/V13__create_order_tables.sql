CREATE TABLE IF NOT EXISTS orders
(
    id          UUID PRIMARY KEY,
    creator_id  UUID REFERENCES users (id),
    status      VARCHAR(50)              NOT NULL,
    worker_id   UUID REFERENCES users (id),
    game_name   VARCHAR(255),
    base_price  DOUBLE PRECISION         NOT NULL,
    total_price DOUBLE PRECISION         NOT NULL,
    total_time  INT                      NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS order_options
(
    id           UUID PRIMARY KEY,
    option_id    UUID REFERENCES offer_options (id),
    option_title VARCHAR(255),
    value        TEXT,
    label        TEXT,
    order_id     UUID REFERENCES orders (id)
);
