BEGIN;
ALTER TABLE orders
    ALTER COLUMN base_price TYPE NUMERIC(19, 2) USING base_price::numeric(19, 2),
    ALTER COLUMN total_price TYPE NUMERIC(19, 2) USING total_price::numeric(19, 2);
COMMIT;

BEGIN;
ALTER TABLE orders
    ADD COLUMN booster_salary NUMERIC(19, 2) DEFAULT NULL;
COMMIT;