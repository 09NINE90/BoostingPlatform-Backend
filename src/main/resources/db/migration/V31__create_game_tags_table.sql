CREATE TABLE game_tags
(
    id                   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    booster_profile_id   UUID    NOT NULL,
    game_id              UUID    NOT NULL,
    verified_by_admin_id UUID,
    verified_by_admin    BOOLEAN NOT NULL DEFAULT false,
    is_active            BOOLEAN NOT NULL DEFAULT true,
    verified_at          TIMESTAMP WITH TIME ZONE,

    CONSTRAINT fk_game_tags_booster_profile
        FOREIGN KEY (booster_profile_id)
            REFERENCES booster_profile (id),

    CONSTRAINT fk_game_tags_game
        FOREIGN KEY (game_id)
            REFERENCES game (id),

    CONSTRAINT fk_game_tags_verified_by_admin
        FOREIGN KEY (verified_by_admin_id)
            REFERENCES users (id)
);

CREATE INDEX idx_game_tags_booster_profile ON game_tags (booster_profile_id);
CREATE INDEX idx_game_tags_game ON game_tags (game_id);
CREATE INDEX idx_game_tags_verified_by ON game_tags (verified_by_admin_id);