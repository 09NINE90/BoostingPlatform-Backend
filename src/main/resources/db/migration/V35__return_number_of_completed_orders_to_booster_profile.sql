BEGIN;
ALTER TABLE booster_profile
    ADD COLUMN number_of_completed_orders INT DEFAULT 0;

ALTER TABLE customer_profile
    ADD COLUMN total_orders           INT            DEFAULT 0,
    ADD COLUMN total_amount_of_orders DECIMAL(19, 4) DEFAULT 0.00 NOT NULL;
COMMIT;
