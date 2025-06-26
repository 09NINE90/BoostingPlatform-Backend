BEGIN;
ALTER TABLE customer_profile
    ALTER COLUMN status SET DEFAULT 'EXPLORER';

ALTER TABLE booster_profile
    DROP COLUMN number_of_completed_orders;
COMMIT;
