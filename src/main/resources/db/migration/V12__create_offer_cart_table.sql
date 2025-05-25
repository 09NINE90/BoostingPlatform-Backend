CREATE TABLE IF NOT EXISTS offer_cart
(
    id          UUID PRIMARY KEY,
    game_name   VARCHAR(255),
    base_price  DOUBLE PRECISION,
    total_price DOUBLE PRECISION,
    total_time  INT,
    offer_id    UUID REFERENCES offers (id),
    creator_id  UUID REFERENCES users (id),
    created_at  TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS offer_option_cart
(
    id            UUID PRIMARY KEY,
    option_id     UUID REFERENCES offer_options (id),
    option_title  VARCHAR(255),
    value         VARCHAR(255),
    label         VARCHAR(255),
    offer_cart_id UUID REFERENCES offer_cart (id)
);