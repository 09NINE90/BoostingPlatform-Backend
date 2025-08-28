CREATE TABLE platforms
(
    id    BIGSERIAL PRIMARY KEY,
    title VARCHAR(50) NOT NULL UNIQUE,
    name  VARCHAR(50) NOT NULL UNIQUE
);

INSERT INTO platforms (title, name)
VALUES ('PS', 'Play Station'),
       ('PC', 'PC'),
       ('XBOX', 'Xbox');

CREATE TABLE game_platforms
(
    game_id     UUID   NOT NULL,
    platform_id BIGINT NOT NULL,
    PRIMARY KEY (game_id, platform_id),
    FOREIGN KEY (game_id) REFERENCES game (id) ON DELETE CASCADE,
    FOREIGN KEY (platform_id) REFERENCES platforms (id) ON DELETE CASCADE
);

ALTER TABLE offer_cart
    ADD COLUMN platform_id BIGINT;
ALTER TABLE offer_cart
    ADD CONSTRAINT fk_offer_cart_platform
        FOREIGN KEY (platform_id) REFERENCES platforms (id);

ALTER TABLE orders
    ADD COLUMN platform_id BIGINT;
ALTER TABLE orders
    ADD CONSTRAINT fk_orders_platform
        FOREIGN KEY (platform_id) REFERENCES platforms (id);

INSERT INTO game_platforms (game_id, platform_id)
SELECT g.id, p.id
FROM game g
         CROSS JOIN platforms p;

UPDATE offer_cart oc
SET platform_id = p.id
FROM platforms p
WHERE oc.game_platform = p.title
  AND p.title IN ('PS', 'PC', 'XBOX');

UPDATE orders o
SET platform_id = p.id
FROM platforms p
WHERE o.game_platform = p.title
  AND p.title IN ('PS', 'PC', 'XBOX');


ALTER TABLE game
    DROP COLUMN platforms;
ALTER TABLE offer_cart
    DROP COLUMN game_platform;
ALTER TABLE orders
    DROP COLUMN game_platform;