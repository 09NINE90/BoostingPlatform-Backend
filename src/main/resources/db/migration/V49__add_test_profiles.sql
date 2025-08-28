INSERT INTO users (id, password, roles, username, enabled, confirmation_token, confirmation_code)
VALUES ('d34995d3-3ffb-48e5-995f-1360b8007858',
        '$2a$10$OG6LEx4/lmHDbsQOyszWhessv0D/SW/xE1thD0PhsDzYzr3E6tXwe',
        'ROLE_BOOSTER',
        'test_booster@testmail.com',
        true,
        NULL,
        NULL);

INSERT INTO user_profile (id, created_at, last_activity_at, nickname, second_id, user_id, image_url, description)
VALUES ('d34995d3-3ffb-48e5-995f-1360b8007851',
        NOW(),
        NOW(),
        'Test Booster',
        'AAAA-1111',
        'd34995d3-3ffb-48e5-995f-1360b8007858',
        NULL,
        'Professional booster with 5 years experience' -- Описание
       );

INSERT INTO booster_profile (id, user_id)
VALUES ('d34995d3-3ffb-48e5-995f-1360b8007111',
        'd34995d3-3ffb-48e5-995f-1360b8007858');

INSERT INTO game_tags (id, booster_profile_id, game_id, verified_by_admin_id, verified_by_admin, is_active,
                       verified_at)
VALUES ('6a4236d5-8ec5-49b8-979d-9e9d409f1111'::uuid, 'd34995d3-3ffb-48e5-995f-1360b8007111'::uuid,
        '3e8e1d1a-5a19-4c6e-b872-7387a1a91e01'::uuid, '79c1d1cd-0be9-4f98-a6be-8974edad3cde'::uuid, true, true,
        NOW()),
       ('7c21808e-a4c8-41a4-8659-84b3d2d58111'::uuid, 'd34995d3-3ffb-48e5-995f-1360b8007111'::uuid,
        'e2bc2767-7d71-4b3a-90a6-3db49b9d6542'::uuid, '79c1d1cd-0be9-4f98-a6be-8974edad3cde'::uuid, true, true,
        NOW()),
       ('7c21808e-a4c8-41a4-8659-84b3d2d58222'::uuid, 'd34995d3-3ffb-48e5-995f-1360b8007111'::uuid,
        '4f92e0f3-5c21-47d5-b1a5-f6d3c8f289c3'::uuid, '79c1d1cd-0be9-4f98-a6be-8974edad3cde'::uuid, true, true,
        NOW()),
       ('7c21808e-a4c8-41a4-8659-84b3d2d58333'::uuid, 'd34995d3-3ffb-48e5-995f-1360b8007111'::uuid,
        'b9c1c0ee-85a4-4f79-92db-9aeaf4a2c9fd'::uuid, '79c1d1cd-0be9-4f98-a6be-8974edad3cde'::uuid, true, true,
        NOW()),
       ('7c21808e-a4c8-41a4-8659-84b3d2d58666'::uuid, 'd34995d3-3ffb-48e5-995f-1360b8007111'::uuid,
        'f6a14376-2628-4a3c-9883-7ffdfb58b126'::uuid, '79c1d1cd-0be9-4f98-a6be-8974edad3cde'::uuid, true, true,
        NOW());


INSERT INTO users (id, password, roles, username, enabled, confirmation_token, confirmation_code)
VALUES ('79c1d1cd-0be9-4f98-a6be-8974edad3ddd',
        '$2a$10$0baavYWO0RMJOAIg4q6SYeW3frHpigiKxHP9fuQyRJSEIt9ESVfz6',
        'ROLE_CUSTOMER',
        'test_customer@testmail.com',
        true,
        NULL,
        NULL);

INSERT INTO user_profile (id, created_at, last_activity_at, nickname, second_id, user_id, image_url, description)
VALUES ('d34995d3-3ffb-48e5-995f-1360b8007777',
        NOW(),
        NOW(),
        'Test Customer',
        'BBBB-2222',
        '79c1d1cd-0be9-4f98-a6be-8974edad3ddd',
        NULL,
        'Hello! It is my status...');

INSERT INTO customer_profile (id, user_id)
VALUES ('d34995d3-3ffb-48e5-995f-1360b8002222',
        '79c1d1cd-0be9-4f98-a6be-8974edad3ddd');