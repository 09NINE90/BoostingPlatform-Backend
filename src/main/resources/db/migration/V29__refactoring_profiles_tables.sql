ALTER TABLE user_profile
    DROP COLUMN level;

CREATE TABLE customer_profile
(
    id                  UUID PRIMARY KEY,
    cashback_balance    DECIMAL(19, 4) DEFAULT 0.00 NOT NULL,
    discount_percentage INT            DEFAULT 1    NOT NULL,
    status              varchar(20)                 NOT NULL DEFAULT 'JUNIOR',
    user_id             UUID                        NOT NULL,
    CONSTRAINT fk_customer_profile_user FOREIGN KEY (user_id) REFERENCES users (id)
);

INSERT INTO booster_profile (id, user_id)
VALUES ('7c21808e-a4c8-41a4-8659-84b3d2d58202'::uuid, '5c21809e-a4c8-49a4-8659-86b3d2d58201'::uuid);

INSERT INTO customer_profile (id, user_id)
VALUES ('1c11111e-a4c8-41a4-8659-84b3d1d88888'::uuid, '79c1d1cd-0be9-4f98-a6be-8974edad3cde'::uuid);