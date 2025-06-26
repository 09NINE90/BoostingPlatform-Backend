ALTER TABLE user_profile
    DROP COLUMN description;

BEGIN;
ALTER TABLE user_profile
    ADD COLUMN description TEXT NULL;
COMMIT;
