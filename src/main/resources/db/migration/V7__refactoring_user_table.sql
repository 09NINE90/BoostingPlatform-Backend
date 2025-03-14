ALTER TABLE users
    DROP COLUMN IF EXISTS confirmation_code;

ALTER TABLE users
    ADD COLUMN confirmation_token TEXT NULL;
