CREATE TABLE dev.offers (
    id          uuid            NOT NULL,
    price       DECIMAL(10, 2)  NOT NULL,
    categories  varchar(255)    NOT NULL,
    created_at  date            NOT NULL DEFAULT NOW(),
    description text            NOT NULL,
    image_url   text            NOT NULL,
    second_id   varchar(255)    NOT NULL,
    title       varchar(255)    NOT NULL,
    creator_id  uuid            NOT NULL,
    game_id     uuid            NOT NULL,
    CONSTRAINT offers_pkey PRIMARY KEY (id),
    CONSTRAINT fk_offers_creator FOREIGN KEY (creator_id) REFERENCES dev."user"(id),
    CONSTRAINT fk_offers_game FOREIGN KEY (game_id) REFERENCES dev.game(id)
);