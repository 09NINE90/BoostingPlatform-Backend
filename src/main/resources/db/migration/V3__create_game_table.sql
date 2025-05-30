CREATE TABLE IF NOT EXISTS game
(
    id             uuid         NOT NULL,
    second_id      varchar(255) NOT NULL,
    description    varchar(255) NOT NULL,
    title          varchar(255) NOT NULL,
    image_url      text         NOT NULL,
    mini_image_url text         NOT NULL,
    creator_id     uuid         NOT NULL,
    rating         int4         NOT NULL DEFAULT 1,
    CONSTRAINT game_pkey PRIMARY KEY (id),
    CONSTRAINT fk_game_user FOREIGN KEY (creator_id) REFERENCES users (id)
);