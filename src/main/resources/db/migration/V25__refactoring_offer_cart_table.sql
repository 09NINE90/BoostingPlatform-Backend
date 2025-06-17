ALTER TABLE offer_cart
    ADD COLUMN game_id UUID;

UPDATE offer_cart oc
SET game_id = g.id
FROM game g
WHERE oc.game_name = g.title;

ALTER TABLE offer_cart
    ADD CONSTRAINT fk_offer_cart_game
        FOREIGN KEY (game_id) REFERENCES game (id);

ALTER TABLE offer_cart
    DROP COLUMN game_name;