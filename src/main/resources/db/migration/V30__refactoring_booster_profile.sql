ALTER TABLE booster_profile
    DROP COLUMN level;

BEGIN;
ALTER TABLE booster_profile
    ADD COLUMN level                      varchar(20) NOT NULL DEFAULT 'ROOKIE',
    ADD COLUMN number_of_completed_orders INT         NOT NULL DEFAULT 0;
COMMIT;

BEGIN;
ALTER TABLE booster_profile
    ALTER COLUMN percentage_of_order SET DEFAULT 0.45;
COMMIT;