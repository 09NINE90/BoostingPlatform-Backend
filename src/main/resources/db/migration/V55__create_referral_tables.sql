CREATE TABLE referral_relations
(
    id                  UUID PRIMARY KEY,
    referrer_id         UUID                     NOT NULL,
    referred_id         UUID                     NOT NULL UNIQUE,
    referred_type       VARCHAR(10)              NOT NULL CHECK (referred_type IN ('CLIENT', 'BOOSTER')),
    referral_percentage NUMERIC(5, 2)            NOT NULL DEFAULT 0.02,
    referral_balance    NUMERIC(19, 4)           NOT NULL DEFAULT 0,
    has_activity        BOOLEAN                  NOT NULL DEFAULT FALSE,
    total_earned        NUMERIC(19, 4)           NOT NULL DEFAULT 0,
    created_at          TIMESTAMP WITH TIME ZONE NOT NULL,
    last_activity_at    TIMESTAMP WITH TIME ZONE,

    CONSTRAINT fk_referral_relations_referrer
        FOREIGN KEY (referrer_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_referral_relations_referred
        FOREIGN KEY (referred_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT chk_referral_relations_not_self
        CHECK (referrer_id != referred_id),
    CONSTRAINT chk_referral_percentage_range
        CHECK (referral_percentage >= 0 AND referral_percentage <= 50),
    CONSTRAINT chk_referral_balance_non_negative
        CHECK (referral_balance >= 0),
    CONSTRAINT chk_total_earned_non_negative
        CHECK (total_earned >= 0)
);

CREATE INDEX idx_referral_relations_referrer_id ON referral_relations (referrer_id);
CREATE INDEX idx_referral_relations_referred_id ON referral_relations (referred_id);
CREATE INDEX idx_referral_relations_created_at ON referral_relations (created_at);
CREATE INDEX idx_referral_relations_has_activity ON referral_relations (has_activity);
CREATE INDEX idx_referral_relations_referrer_activity ON referral_relations (referrer_id, has_activity);