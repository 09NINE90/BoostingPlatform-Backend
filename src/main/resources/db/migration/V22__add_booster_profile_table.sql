CREATE TABLE booster_profile
(
    id                  UUID PRIMARY KEY,
    level               INT              DEFAULT 1    NOT NULL,
    percentage_of_order DOUBLE PRECISION DEFAULT 0.5  NOT NULL,
    balance             DECIMAL(19, 4)   DEFAULT 0.00 NOT NULL,
    total_income        DECIMAL(19, 4)   DEFAULT 0.00 NOT NULL,
    total_tips          DECIMAL(19, 4)   DEFAULT 0.00 NOT NULL,
    user_id             UUID                          NOT NULL,
    CONSTRAINT fk_booster_profile_user FOREIGN KEY (user_id) REFERENCES users (id)
);

