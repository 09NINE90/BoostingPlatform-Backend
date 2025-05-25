-- Вставляем тестовое предложение (offer)
INSERT INTO offers (id, price, categories, created_at, description, image_url, second_id, title, creator_id, game_id)
VALUES ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
        100.00,
        'boosting',
        '2023-01-15',
        'Power leveling service for your character',
        'https://example.com/image1.jpg',
        'OFFER-001',
        'Power Leveling Package',
        '5c21809e-a4c8-49a4-8659-86b3d2d58201', -- ID пользователя
        '3e8e1d1a-5a19-4c6e-b872-7387a1a91e01' -- ID игры
       );

-- Вставляем опции для предложения
-- 1. Опция Region (кнопки)
INSERT INTO offer_options (id, offer_id, option_id, title, type, multiple, parent_item_id)
VALUES ('b1eebc99-9c0b-4ef8-bb6d-6bb9bd380a12',
        'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
        'region',
        'Region',
        'BUTTONS',
        false,
        null);

-- Элементы для опции Region
INSERT INTO option_items (id, option_id, value, label, price_change, time_change)
VALUES ('c2eebc99-9c0b-4ef8-bb6d-6bb9bd380a13',
        'b1eebc99-9c0b-4ef8-bb6d-6bb9bd380a12',
        'EU',
        'Europe',
        0.00,
        0);

INSERT INTO option_items (id, option_id, value, label, price_change, time_change)
VALUES ('d3eebc99-9c0b-4ef8-bb6d-6bb9bd380a14',
        'b1eebc99-9c0b-4ef8-bb6d-6bb9bd380a12',
        'US',
        'United States',
        10.00,
        1);

-- 2. Опция Boost Method (select)
INSERT INTO offer_options (id, offer_id, option_id, title, type, multiple, parent_item_id)
VALUES ('e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a15',
        'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
        'boostMethod',
        'Boost Method',
        'SELECT',
        false,
        null);

-- Элементы для опции Boost Method
-- Self-play вариант
INSERT INTO option_items (id, option_id, value, label, price_change, time_change)
VALUES ('f5eebc99-9c0b-4ef8-bb6d-6bb9bd380a16',
        'e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a15',
        'selfplay',
        'Self-play',
        0.00,
        0);

-- Piloted вариант (с подопциями)
INSERT INTO option_items (id, option_id, value, label, price_change, time_change)
VALUES ('06eebc99-9c0b-4ef8-bb6d-6bb9bd380a17',
        'e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a15',
        'piloted',
        'Piloted',
        20.00,
        2);

-- Подопция для Piloted (Safety Options)
INSERT INTO offer_options (id, offer_id, option_id, title, type, multiple, parent_item_id)
VALUES ('17eebc99-9c0b-4ef8-bb6d-6bb9bd380a18',
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
VALUES ('28eebc99-9c0b-4ef8-bb6d-6bb9bd380a19',
        '17eebc99-9c0b-4ef8-bb6d-6bb9bd380a18',
        'vpn',
        'Use VPN',
        5.00,
        1);

-- Stream вариант
INSERT INTO option_items (id, option_id, value, label, price_change, time_change, percent_change)
VALUES ('39eebc99-9c0b-4ef8-bb6d-6bb9bd380a20',
        '17eebc99-9c0b-4ef8-bb6d-6bb9bd380a18',
        'stream',
        'Live Stream',
        0.00,
        3,
        50.00);

-- Подподопция для VPN (Test 3 level)
INSERT INTO offer_options (id, offer_id, option_id, title, type, multiple, slider_price_change, time_change, min, max,
                           step, parent_item_id)
VALUES ('40eebc99-9c0b-4ef8-bb6d-6bb9bd380a21',
        'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
        'test3level',
        'Level 3 options',
        'SLIDER',
        false,
        1.2,
        0,
        1,
        60,
        5,
        '28eebc99-9c0b-4ef8-bb6d-6bb9bd380a19' -- родительский item (vpn)
       );

-- 3. Опция Raid Time (slider)
INSERT INTO offer_options (id, offer_id, option_id, title, type, multiple, slider_price_change, time_change, min, max,
                           step, parent_item_id)
VALUES ('51eebc99-9c0b-4ef8-bb6d-6bb9bd380a22',
        'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
        'raidTime',
        'Raid Time',
        'SLIDER',
        false,
        2.0,
        0,
        10,
        60,
        5,
        null);

-- 4. Опция Additional Options (checkbox)
INSERT INTO offer_options (id, offer_id, option_id, title, type, multiple, parent_item_id)
VALUES ('62eebc99-9c0b-4ef8-bb6d-6bb9bd380a23',
        'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
        'additionalOptions',
        'Additional Options',
        'CHECKBOX',
        true,
        null);

-- Элементы для Additional Options
INSERT INTO option_items (id, option_id, value, label, price_change, time_change, percent_change)
VALUES ('73eebc99-9c0b-4ef8-bb6d-6bb9bd380a24',
        '62eebc99-9c0b-4ef8-bb6d-6bb9bd380a23',
        'stream',
        'Stream Service',
        10.00,
        2,
        50.00);

INSERT INTO option_items (id, option_id, value, label, price_change, time_change)
VALUES ('84eebc99-9c0b-4ef8-bb6d-6bb9bd380a25',
        '62eebc99-9c0b-4ef8-bb6d-6bb9bd380a23',
        'priority',
        'Priority Order',
        30.00,
        -5);


-- 1. Опция "Сложность" (кнопки)
INSERT INTO offer_options (id, offer_id, option_id, title, type, multiple, parent_item_id)
VALUES ('b1eebc99-9c0b-4ef8-bb6d-6bb9bd380b11',
        'ae9b14d1-1a86-4622-9760-eae403760917',
        'difficulty',
        'Сложность',
        'BUTTONS',
        false,
        null);

-- Элементы для опции Сложность
INSERT INTO option_items (id, option_id, value, label, price_change, time_change)
VALUES ('c2eebc99-9c0b-4ef8-bb6d-6bb9bd380b12', 'b1eebc99-9c0b-4ef8-bb6d-6bb9bd380b11', 'easy', 'Легкая', -50.00, 0),
       ('d3eebc99-9c0b-4ef8-bb6d-6bb9bd380b13', 'b1eebc99-9c0b-4ef8-bb6d-6bb9bd380b11', 'normal', 'Нормальная', 0.00,
        0),
       ('e4eebc99-9c0b-4ef8-bb6d-6bb9bd380b14', 'b1eebc99-9c0b-4ef8-bb6d-6bb9bd380b11', 'hard', 'Сложная', 30.00, 2);

-- 2. Опция "Стиль прохождения" (select с вложенностями)
INSERT INTO offer_options (id, offer_id, option_id, title, type, multiple, parent_item_id)
VALUES ('f5eebc99-9c0b-4ef8-bb6d-6bb9bd380b15',
        'ae9b14d1-1a86-4622-9760-eae403760917',
        'playstyle',
        'Стиль прохождения',
        'SELECT',
        false,
        null);

-- Элементы для Стиля прохождения
INSERT INTO option_items (id, option_id, value, label, price_change, time_change)
VALUES ('06eebc99-9c0b-4ef8-bb6d-6bb9bd380b16', 'f5eebc99-9c0b-4ef8-bb6d-6bb9bd380b15', 'speedrun', 'Спидрам', 40.00,
        3),
       ('17eebc99-9c0b-4ef8-bb6d-6bb9bd380b17', 'f5eebc99-9c0b-4ef8-bb6d-6bb9bd380b15', '100percent', '100% завершение',
        60.00, 5),
       ('28eebc99-9c0b-4ef8-bb6d-6bb9bd380b18', 'f5eebc99-9c0b-4ef8-bb6d-6bb9bd380b15', 'glitchless', 'Без глитчей',
        0.00, 0);

-- Подопция для Спидрама (Категория спидранна)
INSERT INTO offer_options (id, offer_id, option_id, title, type, multiple, parent_item_id)
VALUES ('39eebc99-9c0b-4ef8-bb6d-6bb9bd380b19',
        'ae9b14d1-1a86-4622-9760-eae403760917',
        'speedrun_category',
        'Категория спидранна',
        'SELECT',
        false,
        '06eebc99-9c0b-4ef8-bb6d-6bb9bd380b16' -- родитель: speedrun
       );

-- Элементы для Категории спидранна
INSERT INTO option_items (id, option_id, value, label, price_change, time_change)
VALUES ('40eebc99-9c0b-4ef8-bb6d-6bb9bd380b20', '39eebc99-9c0b-4ef8-bb6d-6bb9bd380b19', 'anypercent', 'Any%', 0.00, 0),
       ('51eebc99-9c0b-4ef8-bb6d-6bb9bd380b21', '39eebc99-9c0b-4ef8-bb6d-6bb9bd380b19', 'allbosses', 'All Bosses',
        20.00, 1);

-- 3. Опция "Дополнительные достижения" (checkbox)
INSERT INTO offer_options (id, offer_id, option_id, title, type, multiple, parent_item_id)
VALUES ('62eebc99-9c0b-4ef8-bb6d-6bb9bd380b22',
        'ae9b14d1-1a86-4622-9760-eae403760917',
        'extra_achievements',
        'Доп. достижения',
        'CHECKBOX',
        true,
        null);

-- Элементы для Доп. достижений
INSERT INTO option_items (id, option_id, value, label, price_change, time_change)
VALUES ('73eebc99-9c0b-4ef8-bb6d-6bb9bd380b23', '62eebc99-9c0b-4ef8-bb6d-6bb9bd380b22', 'no_death', 'Без смертей',
        80.00, 4),
       ('84eebc99-9c0b-4ef8-bb6d-6bb9bd380b24', '62eebc99-9c0b-4ef8-bb6d-6bb9bd380b22', 'all_secrets', 'Все секреты',
        45.00, 3);

-- 4. Опция "Внешний вид" (слайдер)
INSERT INTO offer_options (id, offer_id, option_id, title, type, multiple, slider_price_change, time_change, min, max,
                           step, parent_item_id)
VALUES ('95eebc99-9c0b-4ef8-bb6d-6bb9bd380b25',
        'ae9b14d1-1a86-4622-9760-eae403760917',
        'cosmetic_level',
        'Уровень кастомизации',
        'SLIDER',
        false,
        3.5,
        0,
        1,
        10,
        1,
        null);

-- 5. Опция "Экстренная доставка" (вложенная в "Без смертей")
INSERT INTO offer_options (id, offer_id, option_id, title, type, multiple, parent_item_id)
VALUES ('a6eebc99-9c0b-4ef8-bb6d-6bb9bd380b26',
        'ae9b14d1-1a86-4622-9760-eae403760917',
        'express_delivery',
        'Срочное выполнение',
        'CHECKBOX',
        false,
        '73eebc99-9c0b-4ef8-bb6d-6bb9bd380b23' -- родитель: no_death
       );

-- Элемент для Экстренной доставки
INSERT INTO option_items (id, option_id, value, label, price_change, time_change, percent_change)
VALUES ('b7eebc99-9c0b-4ef8-bb6d-6bb9bd380b27',
        'a6eebc99-9c0b-4ef8-bb6d-6bb9bd380b26',
        'express',
        'Выполнить за 24 часа',
        120.00,
        -3,
        30.00);

-- 6. Опция "Платформы" (кнопки)
INSERT INTO offer_options (id, offer_id, option_id, title, type, multiple, parent_item_id)
VALUES ('c8eebc99-9c0b-4ef8-bb6d-6bb9bd380b28',
        'ae9b14d1-1a86-4622-9760-eae403760917',
        'platform',
        'Платформа',
        'BUTTONS',
        false,
        null);

-- Элементы для Платформ
INSERT INTO option_items (id, option_id, value, label, price_change, time_change)
VALUES ('d9eebc99-9c0b-4ef8-bb6d-6bb9bd380b29', 'c8eebc99-9c0b-4ef8-bb6d-6bb9bd380b28', 'pc', 'PC', 0.00, 0),
       ('e0eebc99-9c0b-4ef8-bb6d-6bb9bd380b30', 'c8eebc99-9c0b-4ef8-bb6d-6bb9bd380b28', 'ps5', 'PlayStation 5', 15.00,
        1),
       ('f1eebc99-9c0b-4ef8-bb6d-6bb9bd380b31', 'c8eebc99-9c0b-4ef8-bb6d-6bb9bd380b28', 'xbox', 'Xbox Series X', 15.00,
        1);


-- 1. Опция "Уровень страха" (слайдер с динамической ценой)
INSERT INTO offer_options (id, offer_id, option_id, title, type, multiple, slider_price_change, time_change, min, max,
                           step, parent_item_id)
VALUES ('b1eebc99-9c0b-4ef8-bb6d-6bb9bd380c11',
        'f6e1a183-0b6d-4f94-9e33-7a1b8e46050f',
        'fear_level',
        'Уровень страха',
        'SLIDER',
        false,
        5.0, -- цена за единицу
        0, -- не влияет на время
        1, -- мин. уровень
        10, -- макс. уровень
        1, -- шаг
        null);

-- 2. Опция "Тип прохождения" (select с вложенностями)
INSERT INTO offer_options (id, offer_id, option_id, title, type, multiple, parent_item_id)
VALUES ('c2eebc99-9c0b-4ef8-bb6d-6bb9bd380c12',
        'f6e1a183-0b6d-4f94-9e33-7a1b8e46050f',
        'playthrough_type',
        'Тип прохождения',
        'SELECT',
        false,
        null);

-- Элементы для Типа прохождения
INSERT INTO option_items (id, option_id, value, label, price_change, time_change)
VALUES ('d3eebc99-9c0b-4ef8-bb6d-6bb9bd380c13', 'c2eebc99-9c0b-4ef8-bb6d-6bb9bd380c12', 'normal', 'Обычное', 0.00, 0),
       ('e4eebc99-9c0b-4ef8-bb6d-6bb9bd380c14', 'c2eebc99-9c0b-4ef8-bb6d-6bb9bd380c12', 'no_lights', 'Без фонарика',
        40.00, 3),
       ('f5eebc99-9c0b-4ef8-bb6d-6bb9bd380c15', 'c2eebc99-9c0b-4ef8-bb6d-6bb9bd380c12', 'pacifist',
        'Пацифист (не убивать)', 60.00, 5);

-- Подопция для "Без фонарика" (Доп. сложность)
INSERT INTO offer_options (id, offer_id, option_id, title, type, multiple, parent_item_id)
VALUES ('06eebc99-9c0b-4ef8-bb6d-6bb9bd380c16',
        'f6e1a183-0b6d-4f94-9e33-7a1b8e46050f',
        'hardcore_mode',
        'Режим хардкора',
        'CHECKBOX',
        true,
        'e4eebc99-9c0b-4ef8-bb6d-6bb9bd380c14' -- родитель: no_lights
       );

-- Элементы для Режима хардкора
INSERT INTO option_items (id, option_id, value, label, price_change, time_change)
VALUES ('17eebc99-9c0b-4ef8-bb6d-6bb9bd380c17', '06eebc99-9c0b-4ef8-bb6d-6bb9bd380c16', 'permadeath', 'Пермадеат',
        80.00, 2),
       ('28eebc99-9c0b-4ef8-bb6d-6bb9bd380c18', '06eebc99-9c0b-4ef8-bb6d-6bb9bd380c16', 'no_saves', 'Без сохранений',
        50.00, 4);

-- 3. Опция "Сбор коллекций" (checkbox с подопциями)
INSERT INTO offer_options (id, offer_id, option_id, title, type, multiple, parent_item_id)
VALUES ('39eebc99-9c0b-4ef8-bb6d-6bb9bd380c19',
        'f6e1a183-0b6d-4f94-9e33-7a1b8e46050f',
        'collections',
        'Сбор коллекций',
        'CHECKBOX',
        true,
        null);

-- Элементы для Сбора коллекций
INSERT INTO option_items (id, option_id, value, label, price_change, time_change)
VALUES ('40eebc99-9c0b-4ef8-bb6d-6bb9bd380c20', '39eebc99-9c0b-4ef8-bb6d-6bb9bd380c19', 'notes', 'Все записи', 25.00,
        1),
       ('51eebc99-9c0b-4ef8-bb6d-6bb9bd380c21', '39eebc99-9c0b-4ef8-bb6d-6bb9bd380c19', 'figurines',
        'Фигурки призраков', 35.00, 2);

-- Подопция для "Все записи" (Формат записей)
INSERT INTO offer_options (id, offer_id, option_id, title, type, multiple, parent_item_id)
VALUES ('62eebc99-9c0b-4ef8-bb6d-6bb9bd380c22',
        'f6e1a183-0b6d-4f94-9e33-7a1b8e46050f',
        'notes_format',
        'Формат записей',
        'SELECT',
        false,
        '40eebc99-9c0b-4ef8-bb6d-6bb9bd380c20' -- родитель: notes
       );

-- Элементы для Формата записей
INSERT INTO option_items (id, option_id, value, label, price_change, time_change)
VALUES ('73eebc99-9c0b-4ef8-bb6d-6bb9bd380c23', '62eebc99-9c0b-4ef8-bb6d-6bb9bd380c22', 'text', 'Только текст', 0.00,
        0),
       ('84eebc99-9c0b-4ef8-bb6d-6bb9bd380c24', '62eebc99-9c0b-4ef8-bb6d-6bb9bd380c22', 'audio', 'Аудиоверсия', 15.00,
        1);

-- 4. Опция "Стрим-сопровождение" (checkbox с особыми условиями)
INSERT INTO offer_options (id, offer_id, option_id, title, type, multiple, parent_item_id)
VALUES ('95eebc99-9c0b-4ef8-bb6d-6bb9bd380c25',
        'f6e1a183-0b6d-4f94-9e33-7a1b8e46050f',
        'streaming',
        'Стрим-сопровождение',
        'CHECKBOX',
        true,
        null);

-- Элементы для Стрим-сопровождения
INSERT INTO option_items (id, option_id, value, label, price_change, time_change, percent_change)
VALUES ('a6eebc99-9c0b-4ef8-bb6d-6bb9bd380c26', '95eebc99-9c0b-4ef8-bb6d-6bb9bd380c25', 'live', 'Прямая трансляция',
        40.00, 0, 20.00),
       ('b7eebc99-9c0b-4ef8-bb6d-6bb9bd380c27', '95eebc99-9c0b-4ef8-bb6d-6bb9bd380c25', 'recorded',
        'Запись прохождения', 25.00, 0, 10.00);

-- 5. Опция "Время суток" (кнопки с эффектами)
INSERT INTO offer_options (id, offer_id, option_id, title, type, multiple, parent_item_id)
VALUES ('c8eebc99-9c0b-4ef8-bb6d-6bb9bd380c28',
        'f6e1a183-0b6d-4f94-9e33-7a1b8e46050f',
        'time_of_day',
        'Время суток',
        'BUTTONS',
        false,
        null);

-- Элементы для Времени суток
INSERT INTO option_items (id, option_id, value, label, price_change, time_change, percent_change)
VALUES ('d9eebc99-9c0b-4ef8-bb6d-6bb9bd380c29', 'c8eebc99-9c0b-4ef8-bb6d-6bb9bd380c28', 'night', 'Ночь (по умолчанию)',
        0.00, 0, 0.00),
       ('e0eebc99-9c0b-4ef8-bb6d-6bb9bd380c30', 'c8eebc99-9c0b-4ef8-bb6d-6bb9bd380c28', 'midnight',
        'Полночь (макс. сложность)', 30.00, 2, 15.00),
       ('f1eebc99-9c0b-4ef8-bb6d-6bb9bd380c31', 'c8eebc99-9c0b-4ef8-bb6d-6bb9bd380c28', 'dusk', 'Сумерки (умеренно)',
        15.00, 1, 5.00);

-- 6. Опция "Экстренная помощь" (вложенная в Пермадеат)
INSERT INTO offer_options (id, offer_id, option_id, title, type, multiple, parent_item_id)
VALUES ('02eebc99-9c0b-4ef8-bb6d-6bb9bd380c32',
        'f6e1a183-0b6d-4f94-9e33-7a1b8e46050f',
        'emergency_help',
        'Экстренная помощь',
        'SELECT',
        false,
        '17eebc99-9c0b-4ef8-bb6d-6bb9bd380c17' -- родитель: permadeath
       );

-- Элементы для Экстренной помощи
INSERT INTO option_items (id, option_id, value, label, price_change, time_change)
VALUES ('13eebc99-9c0b-4ef8-bb6d-6bb9bd380c33', '02eebc99-9c0b-4ef8-bb6d-6bb9bd380c32', 'none', 'Без помощи', 0.00, 0),
       ('24eebc99-9c0b-4ef8-bb6d-6bb9bd380c34', '02eebc99-9c0b-4ef8-bb6d-6bb9bd380c32', 'one_time',
        'Одноразовая помощь', 50.00, -1),
       ('35eebc99-9c0b-4ef8-bb6d-6bb9bd380c35', '02eebc99-9c0b-4ef8-bb6d-6bb9bd380c32', 'full_support',
        'Полное сопровождение', 120.00, -2);