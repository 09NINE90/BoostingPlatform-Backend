-- Вставляем тестовое предложение (offer)
INSERT INTO offers (id, price, categories, created_at, description, image_url, second_id, title, creator_id, game_id)
VALUES (
           'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
           100.00,
           'boosting',
           '2023-01-15',
           'Power leveling service for your character',
           'https://example.com/image1.jpg',
           'OFFER-001',
           'Power Leveling Package',
           '5c21809e-a4c8-49a4-8659-86b3d2d58201', -- ID пользователя
           '3e8e1d1a-5a19-4c6e-b872-7387a1a91e01'  -- ID игры
       );

-- Вставляем опции для предложения
-- 1. Опция Region (кнопки)
INSERT INTO offer_options (id, offer_id, option_id, title, type, multiple, parent_item_id)
VALUES (
           'b1eebc99-9c0b-4ef8-bb6d-6bb9bd380a12',
           'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
           'region',
           'Region',
           'BUTTONS',
           false,
           null
       );

-- Элементы для опции Region
INSERT INTO option_items (id, option_id, value, label, price_change, time_change)
VALUES (
           'c2eebc99-9c0b-4ef8-bb6d-6bb9bd380a13',
           'b1eebc99-9c0b-4ef8-bb6d-6bb9bd380a12',
           'EU',
           'Europe',
           0.00,
           0
       );

INSERT INTO option_items (id, option_id, value, label, price_change, time_change)
VALUES (
           'd3eebc99-9c0b-4ef8-bb6d-6bb9bd380a14',
           'b1eebc99-9c0b-4ef8-bb6d-6bb9bd380a12',
           'US',
           'United States',
           10.00,
           1
       );

-- 2. Опция Boost Method (select)
INSERT INTO offer_options (id, offer_id, option_id, title, type, multiple, parent_item_id)
VALUES (
           'e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a15',
           'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
           'boostMethod',
           'Boost Method',
           'SELECT',
           false,
           null
       );

-- Элементы для опции Boost Method
-- Self-play вариант
INSERT INTO option_items (id, option_id, value, label, price_change, time_change)
VALUES (
           'f5eebc99-9c0b-4ef8-bb6d-6bb9bd380a16',
           'e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a15',
           'selfplay',
           'Self-play',
           0.00,
           0
       );

-- Piloted вариант (с подопциями)
INSERT INTO option_items (id, option_id, value, label, price_change, time_change)
VALUES (
           '06eebc99-9c0b-4ef8-bb6d-6bb9bd380a17',
           'e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a15',
           'piloted',
           'Piloted',
           20.00,
           2
       );

-- Подопция для Piloted (Safety Options)
INSERT INTO offer_options (id, offer_id, option_id, title, type, multiple, parent_item_id)
VALUES (
           '17eebc99-9c0b-4ef8-bb6d-6bb9bd380a18',
           'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
           'safety',
           'Safety Options',
           'CHECKBOX',
           true,
           '06eebc99-9c0b-4ef8-bb6d-6bb9bd380a17' -- родительский item (piloted)
       );

-- Элементы для подопции Safety Options
-- VPN вариант
INSERT INTO option_items (id, option_id, value, label, price_change, time_change)
VALUES (
           '28eebc99-9c0b-4ef8-bb6d-6bb9bd380a19',
           '17eebc99-9c0b-4ef8-bb6d-6bb9bd380a18',
           'vpn',
           'Use VPN',
           5.00,
           1
       );

-- Stream вариант
INSERT INTO option_items (id, option_id, value, label, price_change, time_change, percent_change)
VALUES (
           '39eebc99-9c0b-4ef8-bb6d-6bb9bd380a20',
           '17eebc99-9c0b-4ef8-bb6d-6bb9bd380a18',
           'stream',
           'Live Stream',
           0.00,
           3,
           50.00
       );

-- Подподопция для VPN (Test 3 level)
INSERT INTO offer_options (id, offer_id, option_id, title, type, multiple, min, max, step, parent_item_id)
VALUES (
           '40eebc99-9c0b-4ef8-bb6d-6bb9bd380a21',
           'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
           'test3level',
           'Level 3 options',
           'SLIDER',
           false,
           20,
           60,
           5,
           '28eebc99-9c0b-4ef8-bb6d-6bb9bd380a19' -- родительский item (vpn)
       );

-- 3. Опция Raid Time (slider)
INSERT INTO offer_options (id, offer_id, option_id, title, type, multiple, min, max, step, parent_item_id)
VALUES (
           '51eebc99-9c0b-4ef8-bb6d-6bb9bd380a22',
           'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
           'raidTime',
           'Raid Time',
           'SLIDER',
           false,
           20,
           60,
           5,
           null
       );

-- 4. Опция Additional Options (checkbox)
INSERT INTO offer_options (id, offer_id, option_id, title, type, multiple, parent_item_id)
VALUES (
           '62eebc99-9c0b-4ef8-bb6d-6bb9bd380a23',
           'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
           'additionalOptions',
           'Additional Options',
           'CHECKBOX',
           true,
           null
       );

-- Элементы для Additional Options
INSERT INTO option_items (id, option_id, value, label, price_change, time_change, percent_change)
VALUES (
           '73eebc99-9c0b-4ef8-bb6d-6bb9bd380a24',
           '62eebc99-9c0b-4ef8-bb6d-6bb9bd380a23',
           'stream',
           'Stream Service',
           10.00,
           2,
           50.00
       );

INSERT INTO option_items (id, option_id, value, label, price_change, time_change)
VALUES (
           '84eebc99-9c0b-4ef8-bb6d-6bb9bd380a25',
           '62eebc99-9c0b-4ef8-bb6d-6bb9bd380a23',
           'priority',
           'Priority Order',
           30.00,
           -5
       );