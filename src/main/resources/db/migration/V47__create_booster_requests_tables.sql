CREATE TABLE become_booster_request
(
    id                  UUID PRIMARY KEY,
    nickname            VARCHAR(255)             NOT NULL,
    email               VARCHAR(255)             NOT NULL,
    discord_tag         VARCHAR(255),
    custom_games        TEXT,
    gaming_experience   TEXT                     NOT NULL,
    boosting_experience TEXT                     NOT NULL,
    tracker_links       TEXT,
    progress_images     TEXT,
    additional_info     TEXT,
    created_at          TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status              VARCHAR(20)
);

CREATE TABLE booster_selected_games
(
    request_id UUID         NOT NULL,
    game_name  VARCHAR(255) NOT NULL,
    PRIMARY KEY (request_id, game_name),
    CONSTRAINT fk_request_id
        FOREIGN KEY (request_id)
            REFERENCES become_booster_request (id)
            ON DELETE CASCADE
);

CREATE INDEX idx_booster_request_status ON become_booster_request (status);