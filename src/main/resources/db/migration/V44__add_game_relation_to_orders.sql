ALTER TABLE orders ADD COLUMN game_id UUID;

UPDATE orders o
SET game_id = (
    SELECT g.id
    FROM game g
    WHERE LOWER(g.title) = LOWER(o.game_name)
)
WHERE o.game_name IS NOT NULL;

ALTER TABLE orders
    ADD CONSTRAINT fk_orders_game
        FOREIGN KEY (game_id) REFERENCES game(id);

ALTER TABLE orders DROP COLUMN game_name;
