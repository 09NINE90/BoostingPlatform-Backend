CREATE TABLE offer_cart
(
    id          UUID PRIMARY KEY,
    game_name   VARCHAR(255),
    base_price  DOUBLE PRECISION,
    total_price DOUBLE PRECISION,
    total_time  INT,
    offer_id    UUID REFERENCES offers (id),
    creator_id  UUID REFERENCES users (id),
    created_at  TIMESTAMP NOT NULL,
    updated_at  TIMESTAMP NOT NULL
);

COMMENT ON TABLE offer_cart IS 'Корзина предложений';
COMMENT ON COLUMN offer_cart.id IS 'Уникальный идентификатор корзины';
COMMENT ON COLUMN offer_cart.game_name IS 'Название игры';
COMMENT ON COLUMN offer_cart.base_price IS 'Базовая цена предложения';
COMMENT ON COLUMN offer_cart.total_price IS 'Итоговая цена с учетом опций';
COMMENT ON COLUMN offer_cart.total_time IS 'Общее время выполнения в часах';

CREATE TABLE offer_option_cart
(
    id            UUID PRIMARY KEY,
    option_id     UUID REFERENCES offer_options (id),
    option_title  VARCHAR(255),
    value         VARCHAR(255),
    label         VARCHAR(255),
    offer_cart_id UUID REFERENCES offer_cart (id)
);