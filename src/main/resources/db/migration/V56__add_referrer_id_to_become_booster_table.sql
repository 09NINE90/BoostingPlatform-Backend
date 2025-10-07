ALTER TABLE become_booster_request
    ADD COLUMN referrer_id UUID;

ALTER TABLE become_booster_request
    ADD CONSTRAINT fk_booster_request_referrer
        FOREIGN KEY (referrer_id) REFERENCES users (id);

CREATE INDEX idx_booster_request_referrer ON become_booster_request (referrer_id);