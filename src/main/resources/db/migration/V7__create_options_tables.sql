CREATE TABLE offer_options (
   id UUID PRIMARY KEY,
   offer_id UUID NOT NULL,
   option_id VARCHAR(255) NOT NULL,
   title VARCHAR(255) NOT NULL,
   type VARCHAR(50) NOT NULL,
   multiple BOOLEAN NOT NULL DEFAULT FALSE,
   min INTEGER,
   max INTEGER,
   step INTEGER,
   parent_item_id UUID,

   CONSTRAINT fk_offer_options_offer
       FOREIGN KEY (offer_id)
           REFERENCES offers(id)
           ON DELETE CASCADE
);

CREATE TABLE option_items (
  id UUID PRIMARY KEY,
  option_id UUID NOT NULL,
  value VARCHAR(255) NOT NULL,
  label VARCHAR(255) NOT NULL,
  price_change DECIMAL(10, 2),
  time_change INTEGER,
  percent_change DECIMAL(5, 2)
);

ALTER TABLE option_items
    ADD CONSTRAINT fk_option_items_option
        FOREIGN KEY (option_id)
            REFERENCES offer_options(id)
            ON DELETE CASCADE;

ALTER TABLE offer_options
    ADD CONSTRAINT fk_offer_options_parent_item
        FOREIGN KEY (parent_item_id)
            REFERENCES option_items(id)
            ON DELETE CASCADE;

CREATE INDEX idx_offer_options_offer_id ON offer_options(offer_id);
CREATE INDEX idx_offer_options_parent_item_id ON offer_options(parent_item_id);
CREATE INDEX idx_option_items_option_id ON option_items(option_id);