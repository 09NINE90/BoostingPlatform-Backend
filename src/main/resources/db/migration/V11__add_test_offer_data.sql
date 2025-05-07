-- Создание нового предложения
INSERT INTO offers (
    id, price, categories, created_at, description, image_url, second_id, title, creator_id, game_id
) VALUES (
             'b1a2c3d4-e5f6-7890-abcd-ef1234567890',
             120.00,
             'RPG',
             '2025-05-05',
             'Совместное прохождение основных боссов, получение достижений и трофеев.',
             'https://lfcarry.com/_next/image?url=https%3A%2F%2Flfcarry.com%2Fimg%2Fcustom%2Fcover_67add9b673b5d.webp&w=640&q=75',
             'ER-COOP-001',
             'Co-op Boss Rush',
             '5c21809e-a4c8-49a4-8659-86b3d2d58201',  -- пример ID пользователя
             '3e8e1d1a-5a19-4c6e-b872-7387a1a91e01'   -- пример ID игры
         );

-- Опции для предложения
INSERT INTO offer_options (
    id, offer_id, option_id, title, type, multiple, slider_price_change, min, max, step, parent_item_id
) VALUES
      -- Уровень сложности (ползунок)
      (
          '11111111-1111-1111-1111-111111111111',
          'b1a2c3d4-e5f6-7890-abcd-ef1234567890',
          'difficulty_level',
          'Уровень сложности',
          'SLIDER',
          false,
          20.00,
          1,
          5,
          1,
          NULL
      ),
      -- Стрим-сопровождение (выбор)
      (
          '22222222-2222-2222-2222-222222222222',
          'b1a2c3d4-e5f6-7890-abcd-ef1234567890',
          'streaming_option',
          'Стрим-сопровождение',
          'SELECT',
          false,
          NULL,
          NULL,
          NULL,
          NULL,
          NULL
      ),
      -- Коллекция достижений (множественный выбор)
      (
          '33333333-3333-3333-3333-333333333333',
          'b1a2c3d4-e5f6-7890-abcd-ef1234567890',
          'achievements_collection',
          'Коллекция достижений',
          'CHECKBOX',
          true,
          NULL,
          NULL,
          NULL,
          NULL,
          NULL
      ),
      -- Время запуска (кнопки)
      (
          '44444444-4444-4444-4444-444444444444',
          'b1a2c3d4-e5f6-7890-abcd-ef1234567890',
          'play_time',
          'Время запуска',
          'BUTTONS',
          false,
          NULL,
          NULL,
          NULL,
          NULL,
          NULL
      );

-- Элементы для опции "Стрим-сопровождение"
INSERT INTO option_items (
    id, option_id, value, label, price_change, time_change, percent_change
) VALUES
      (
          '22222222-0000-0000-0000-222222222222',
          '22222222-2222-2222-2222-222222222222',
          'none',
          'Без стрима',
          0.00,
          0,
          NULL
      ),
      (
          '22222222-0000-0000-0000-333333333333',
          '22222222-2222-2222-2222-222222222222',
          'live',
          'Прямая трансляция',
          40.00,
          0,
          NULL
      ),
      (
          '22222222-0000-0000-0000-444444444444',
          '22222222-2222-2222-2222-222222222222',
          'recorded',
          'Запись прохождения',
          25.00,
          0,
          NULL
      );

-- Элементы для опции "Коллекция достижений"
INSERT INTO option_items (
    id, option_id, value, label, price_change, time_change, percent_change
) VALUES
      (
          '33333333-0000-0000-0000-333333333333',
          '33333333-3333-3333-3333-333333333333',
          'all_achievements',
          'Все достижения',
          30.00,
          0,
          NULL
      ),
      (
          '33333333-0000-0000-0000-333333333334',
          '33333333-3333-3333-3333-333333333333',
          'rare_trophies',
          'Редкие трофеи',
          50.00,
          1,
          NULL
      );

-- Элементы для опции "Время запуска"
INSERT INTO option_items (
    id, option_id, value, label, price_change, time_change, percent_change
) VALUES
      (
          '44444444-0000-0000-0000-444444444444',
          '44444444-4444-4444-4444-444444444444',
          'day',
          'Дневное время',
          0.00,
          0,
          NULL
      ),
      (
          '44444444-0000-0000-0000-444444444445',
          '44444444-4444-4444-4444-444444444444',
          'evening',
          'Вечернее время',
          10.00,
          1,
          NULL
      ),
      (
          '44444444-0000-0000-0000-444444444446',
          '44444444-4444-4444-4444-444444444444',
          'night',
          'Ночное время',
          20.00,
          2,
          NULL
      );

-- Секции для предложения
INSERT INTO offer_section (
    id, offer_id, title, type, description
) VALUES
      (
          '55555555-5555-5555-5555-555555555555',
          'b1a2c3d4-e5f6-7890-abcd-ef1234567890',
          'Что вы получите',
          'LIST',
          'Основные преимущества нашего коопа'
      ),
      (
          '66666666-6666-6666-6666-666666666666',
          'b1a2c3d4-e5f6-7890-abcd-ef1234567890',
          'Требования',
          'LIST',
          'Необходимые условия для участия'
      ),
      (
          '77777777-7777-7777-7777-777777777777',
          'b1a2c3d4-e5f6-7890-abcd-ef1234567890',
          'Дополнительные опции',
          'ACCORDION_LIST',
          'Вы можете выбрать дополнительные опции'
      );

-- Элементы секций
-- Что вы получите
INSERT INTO offer_section_item (
    title, type, description, related_offer_id, image_url, price, parent_id, section_id
) VALUES
      (
          'Прохождение боссов с экспертом',
          'SIMPLE',
          'Прохождение боссов с экспертом', NULL, NULL, NULL, NULL,
          '55555555-5555-5555-5555-555555555555'
      ),
      (
          'Получение достижений и трофеев',
          'SIMPLE',
          'Получение достижений и трофеев', NULL, NULL, NULL, NULL,
          '55555555-5555-5555-5555-555555555555'
      ),
      (
          'Совместный стрим на ваш канал',
          'SIMPLE',
          'Совместный стрим на ваш канал', NULL, NULL, NULL, NULL,
          '55555555-5555-5555-5555-555555555555'
      );

-- Требования
INSERT INTO offer_section_item (
    title, type, description, related_offer_id, image_url, price, parent_id, section_id
) VALUES
    (
        'Минимальный уровень персонажа: 100',
        'SIMPLE',
        'Минимальный уровень персонажа: 100', NULL, NULL, NULL, NULL,
        '66666666-6666-6666-6666-666666666666'
    );

-- Дополнительные опции
INSERT INTO offer_section_item (
    title, type, description, related_offer_id, image_url, price, parent_id, section_id
) VALUES
      (
          'Стрим-сопровождение',
          'SIMPLE',
          'Выберите в дополнительных опциях стрим-сопровождение',
          NULL, NULL, NULL, NULL,
          '77777777-7777-7777-7777-777777777777'
      ),
      (
          'Коллекция достижений',
          'SIMPLE',
          'Выберите опцию Коллекция достижений для сбора достижений',
          NULL, NULL, NULL, NULL,
          '77777777-7777-7777-7777-777777777777'
      );


-- Создание нового предложения “Finality’s Auger Boost”
INSERT INTO offers (
    id, price, categories, created_at, description, image_url, second_id, title, creator_id, game_id
) VALUES (
             'd2f763aa-3e51-4d4b-a906-1b2e3f4c5d6e',
             40.00,
             'FPS, Destiny 2',
             '2025-05-05',
             'Пилотируемое прохождение миссии Finality’s Auger в Destiny 2 без катализатора, без прохождения Лабиринта Тонущего, без полного данжа и без стрим-сопровождения.',
             'https://lfcarry.com/_next/image?url=https%3A%2F%2Flfcarry.com%2Fimg%2Fcustom%2Fcover_67add9b673b5d.webp&w=640&q=75',
             'D2-FA-001',
             'Finality’s Auger Boost',
             '79c1d1cd-0be9-4f98-a6be-8974edad3cde',
             'e2bc2767-7d71-4b3a-90a6-3db49b9d6542'
         );

-- Опции для предложения
INSERT INTO offer_options (
    id, offer_id, option_id, title, type, multiple, slider_price_change, min, max, step, parent_item_id
) VALUES
      ('0f1e2d3c-4b5a-6978-8f9e-0d1c2b3a4e5f', 'd2f763aa-3e51-4d4b-a906-1b2e3f4c5d6e', 'boost_method',                 'Метод прохождения',               'SELECT',  FALSE, NULL, NULL, NULL, NULL, NULL),
      ('1a2b3c4d-5e6f-7089-9e0d-1c2b3a4f5e6d', 'd2f763aa-3e51-4d4b-a906-1b2e3f4c5d6e', 'catalyst_options',             'Катализатор',                     'SELECT',  FALSE, NULL, NULL, NULL, NULL, NULL),
      ('2b3c4d5e-6f7a-8190-0f1e-2d3c4b5a6e7f', 'd2f763aa-3e51-4d4b-a906-1b2e3f4c5d6e', 'drowning_labyrinth_questline', 'Квест "Тонущий Лабиринт"',       'SELECT',  FALSE, NULL, NULL, NULL, NULL, NULL),
      ('3c4d5e6f-7a8b-9201-1a2b-3c4d5e6f7a8b', 'd2f763aa-3e51-4d4b-a906-1b2e3f4c5d6e', 'full_dungeon',                 'Полный данж',                     'SELECT',  FALSE, NULL, NULL, NULL, NULL, NULL),
      ('4d5e6f7a-8b9c-0312-2b3c-4d5e6f7a8b9c', 'd2f763aa-3e51-4d4b-a906-1b2e3f4c5d6e', 'streaming',                    'Стрим-сопровождение',              'SELECT',  FALSE, NULL, NULL, NULL, NULL, NULL
      );

-- Значения для опции “Метод прохождения”
INSERT INTO option_items (
    id, option_id, value, label, price_change, time_change, percent_change
) VALUES
      ('5e6f7a8b-9c0d-1324-3c4d-5e6f7a8b9c0d', '0f1e2d3c-4b5a-6978-8f9e-0d1c2b3a4e5f', 'piloted', 'Пилотируемый режим',  0.00, 0, NULL),
      ('6f7a8b9c-0d1e-2435-4d5e-6f7a8b9c0d1e', '0f1e2d3c-4b5a-6978-8f9e-0d1c2b3a4e5f', 'guided',  'С поддержкой гида',   10.00, 1, NULL
      );

-- Значения для опции “Катализатор”
INSERT INTO option_items (
    id, option_id, value, label, price_change, time_change, percent_change
) VALUES
      ('7a8b9c0d-1e2f-3546-5e6f-7a8b9c0d1e2f', '1a2b3c4d-5e6f-7089-9e0d-1c2b3a4f5e6d', 'no_catalyst',  'Катализатор не нужен',  0.00, 0, NULL),
      ('8b9c0d1e-2f3a-4657-6f7a-8b9c0d1e2f3a', '1a2b3c4d-5e6f-7089-9e0d-1c2b3a4f5e6d', 'need_catalyst','Нужен катализатор',     15.00, 0, NULL
      );

-- Значения для опции “Квест "Тонущий Лабиринт"”
INSERT INTO option_items (
    id, option_id, value, label, price_change, time_change, percent_change
) VALUES
      ('9c0d1e2f-3a4b-5768-7a8b-9c0d1e2f3a4b', '2b3c4d5e-6f7a-8190-0f1e-2d3c4b5a6e7f', 'skip',    'Не выполнять',        0.00, 0, NULL),
      ('0d1e2f3a-4b5c-6879-8b9c-0d1e2f3a4b5c', '2b3c4d5e-6f7a-8190-0f1e-2d3c4b5a6e7f', 'include', 'Выполнить',          20.00, 0, NULL
      );

-- Значения для опции “Полный данж”
INSERT INTO option_items (
    id, option_id, value, label, price_change, time_change, percent_change
) VALUES
      ('1e2f3a4b-5c6d-7980-9c0d-1e2f3a4b5c6d', '3c4d5e6f-7a8b-9201-1a2b-3c4d5e6f7a8b', 'start_only','Только задание',       0.00, 0, NULL),
      ('2f3a4b5c-6d7e-8091-0d1e-2f3a4b5c6d7e', '3c4d5e6f-7a8b-9201-1a2b-3c4d5e6f7a8b', 'full_dungeon','Полный проход данжа',25.00, 0, NULL
      );

-- Значения для опции “Стрим-сопровождение”
INSERT INTO option_items (
    id, option_id, value, label, price_change, time_change, percent_change
) VALUES
      ('3a4b5c6d-7e8f-9102-1e2f-3a4b5c6d7e8f', '4d5e6f7a-8b9c-0312-2b3c-4d5e6f7a8b9c', 'no_stream','Без стрима',          0.00, 0, NULL),
      ('4b5c6d7e-8f9a-1023-2f3a-4b5c6d7e8f9a', '4d5e6f7a-8b9c-0312-2b3c-4d5e6f7a8b9c', 'stream',   'С трансляцией',       30.00, 0, NULL
      );

-- Секции для предложения
INSERT INTO offer_section (
    id, offer_id, title, type, description
) VALUES
      ('12c34e56-78fa-4bcd-8123-45ef6789abcd', 'd2f763aa-3e51-4d4b-a906-1b2e3f4c5d6e', 'What you will get',        'LIST',           'Что вы получите'),
      ('23d45f67-89ab-4cde-9234-56fa7890bcde', 'd2f763aa-3e51-4d4b-a906-1b2e3f4c5d6e', 'Catalyst options',         'LIST',           'Опции катализатора'),
      ('34e56a78-9abc-4def-a234-67fb8901cdef', 'd2f763aa-3e51-4d4b-a906-1b2e3f4c5d6e', 'Additional options',       'LIST',           'Вы можете выбрать дополнительные опции'),
      ('45f67b89-abcd-4123-b345-78fc9012def0', 'd2f763aa-3e51-4d4b-a906-1b2e3f4c5d6e', 'How it works',            'ACCORDION_LIST', 'Как это работает'),
      ('56a78c90-bcde-4e12-c456-89fd0123ef01', 'd2f763aa-3e51-4d4b-a906-1b2e3f4c5d6e', 'Requirements',            'ACCORDION_LIST', 'Требования к игре');

-- Элементы секции "What you will get"
INSERT INTO offer_section_item (
    title, type, description, related_offer_id, image_url, price, parent_id, section_id
) VALUES
      ('Получение Finality’s Auger',           'SIMPLE', NULL, NULL, NULL, NULL, NULL, '12c34e56-78fa-4bcd-8123-45ef6789abcd'),
      ('Пилотируемое прохождение миссии',      'SIMPLE', NULL, NULL, NULL, NULL, NULL, '12c34e56-78fa-4bcd-8123-45ef6789abcd'),
      ('Гарантированный дроп оружия',          'SIMPLE', NULL, NULL, NULL, NULL, NULL, '12c34e56-78fa-4bcd-8123-45ef6789abcd');

-- Элементы секции "Catalyst options"
INSERT INTO offer_section_item (
    title, type, description, related_offer_id, image_url, price, parent_id, section_id
) VALUES
      ('Катализатор не нужен',               'SIMPLE', NULL, NULL, NULL, NULL, NULL, '23d45f67-89ab-4cde-9234-56fa7890bcde'),
      ('Нужен катализатор',                  'SIMPLE', NULL, NULL, NULL, NULL, NULL, '23d45f67-89ab-4cde-9234-56fa7890bcde');

-- Элементы секции "Additional options"
INSERT INTO offer_section_item (
    title, type, description, related_offer_id, image_url, price, parent_id, section_id
) VALUES
      ('Квест "Тонущий Лабиринт"',          'SIMPLE', NULL, NULL, NULL, NULL, NULL, '34e56a78-9abc-4def-a234-67fb8901cdef'),
      ('Полный данж',                        'SIMPLE', NULL, NULL, NULL, NULL, NULL, '34e56a78-9abc-4def-a234-67fb8901cdef'),
      ('Стрим-сопровождение',                'SIMPLE', NULL, NULL, NULL, NULL, NULL, '34e56a78-9abc-4def-a234-67fb8901cdef');

-- Элементы секции "How it works"
INSERT INTO offer_section_item (
    title, type, description, related_offer_id, image_url, price, parent_id, section_id
) VALUES
      ('Шаг 1: Выбор метода прохождения',    'SIMPLE', 'Выберите между пилотируемым режимом и поддержкой гида', NULL, NULL, NULL, NULL, '45f67b89-abcd-4123-b345-78fc9012def0'),
      ('Шаг 2: Настройка опций',             'SIMPLE', 'Укажите параметры катализатора, полный данж и т.д.',         NULL, NULL, NULL, NULL, '45f67b89-abcd-4123-b345-78fc9012def0'),
      ('Шаг 3: Запуск и выполнение',         'SIMPLE', 'Мы выполняем миссию для вас и доставляем результат',          NULL, NULL, NULL, NULL, '45f67b89-abcd-4123-b345-78fc9012def0');

-- Элементы секции "Requirements"
INSERT INTO offer_section_item (
    title, type, description, related_offer_id, image_url, price, parent_id, section_id
) VALUES
      ('Учетная запись Destiny 2 с расширениями', 'SIMPLE', NULL, NULL, NULL, NULL, NULL, '56a78c90-bcde-4e12-c456-89fd0123ef01'),
      ('Уровень мощности персонажа ≥ 1500',      'SIMPLE', NULL, NULL, NULL, NULL, NULL, '56a78c90-bcde-4e12-c456-89fd0123ef01');
