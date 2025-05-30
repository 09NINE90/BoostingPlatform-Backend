CREATE TABLE offer_options
(
    id                  UUID PRIMARY KEY,
    offer_id            UUID REFERENCES offers (id),
    option_id           VARCHAR(255),
    title               VARCHAR(255),
    type                VARCHAR(50),
    multiple            BOOLEAN,
    slider_price_change DOUBLE PRECISION,
    time_change         INT,
    min                 INT,
    max                 INT,
    step                INT,
    parent_item_id      UUID
);

CREATE TABLE option_items
(
    id             UUID PRIMARY KEY,
    option_id      UUID         NOT NULL,
    value          VARCHAR(255) NOT NULL,
    label          VARCHAR(255) NOT NULL,
    price_change   DECIMAL(10, 2),
    time_change    INTEGER,
    percent_change DECIMAL(5, 2)
);

ALTER TABLE offer_options
    ADD CONSTRAINT fk_parent_item_id_option
        FOREIGN KEY (parent_item_id)
            REFERENCES option_items (id)
            ON DELETE CASCADE;

ALTER TABLE option_items
    ADD CONSTRAINT fk_option_items_option
        FOREIGN KEY (option_id)
            REFERENCES offer_options (id)
            ON DELETE CASCADE;

ALTER TABLE offer_options
    ADD CONSTRAINT fk_offer_options_parent_item
        FOREIGN KEY (parent_item_id)
            REFERENCES option_items (id)
            ON DELETE CASCADE;

CREATE INDEX idx_offer_options_offer_id ON offer_options (offer_id);
CREATE INDEX idx_offer_options_parent_item_id ON offer_options (parent_item_id);
CREATE INDEX idx_option_items_option_id ON option_items (option_id);