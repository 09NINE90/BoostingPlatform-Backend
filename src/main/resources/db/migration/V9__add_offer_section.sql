CREATE TABLE IF NOT EXISTS offer_section
(
    id          UUID PRIMARY KEY,
    offer_id    UUID        NOT NULL,
    title       VARCHAR(255),
    type        VARCHAR(50) NOT NULL,
    description TEXT,

    CONSTRAINT fk_offer_section_offer
        FOREIGN KEY (offer_id) REFERENCES offers (id)
            ON DELETE CASCADE
);

CREATE TABLE offer_section_item
(
    id               BIGSERIAL PRIMARY KEY,
    title            VARCHAR(255),
    type             VARCHAR(50) NOT NULL,
    description      TEXT,
    related_offer_id UUID,
    image_url        VARCHAR(512),
    price            NUMERIC(10, 2),
    parent_id        BIGINT,
    section_id       UUID        NOT NULL,

    CONSTRAINT fk_item_parent
        FOREIGN KEY (parent_id) REFERENCES offer_section_item (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_item_section
        FOREIGN KEY (section_id) REFERENCES offer_section (id)
            ON DELETE CASCADE
);

CREATE INDEX idx_offer_section_offer_id ON offer_section(offer_id);
CREATE INDEX idx_section_item_section_id ON offer_section_item(section_id);
CREATE INDEX idx_section_item_parent_id ON offer_section_item(parent_id);