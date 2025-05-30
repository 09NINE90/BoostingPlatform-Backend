CREATE TABLE IF NOT EXISTS user_profile
(
    id               uuid         NOT NULL,
    created_at       date         NOT NULL DEFAULT NOW(),
    last_activity_at date         NULL,
    nickname         varchar(255) NOT NULL,
    second_id        varchar(255) NOT NULL,
    user_id          uuid         NOT NULL,
    CONSTRAINT uq_user_profile_user_id UNIQUE (user_id),
    CONSTRAINT user_profile_pkey PRIMARY KEY (id),
    CONSTRAINT fk_user_profile_user FOREIGN KEY (user_id) REFERENCES users (id)
);