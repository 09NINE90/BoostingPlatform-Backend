CREATE TABLE offer_cart
(
    id          UUID PRIMARY KEY,
    game_name   VARCHAR(255)   NOT NULL,
    base_price  DECIMAL(10, 2) NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    total_time  INTEGER        NOT NULL,
    creator_id  uuid           NULL,
    offer_id    uuid           NULL,
    created_at  TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_offer_cart_user FOREIGN KEY (creator_id) REFERENCES users (id),
    CONSTRAINT fk_offer_cart_offer FOREIGN KEY (offer_id) REFERENCES offers (id)
);

COMMENT ON TABLE offer_cart IS 'Корзина предложений';
COMMENT ON COLUMN offer_cart.id IS 'Уникальный идентификатор корзины';
COMMENT ON COLUMN offer_cart.game_name IS 'Название игры';
COMMENT ON COLUMN offer_cart.base_price IS 'Базовая цена предложения';
COMMENT ON COLUMN offer_cart.total_price IS 'Итоговая цена с учетом опций';
COMMENT ON COLUMN offer_cart.total_time IS 'Общее время выполнения в часах';