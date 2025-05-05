-- Создание нового предложения
INSERT INTO offers (
    id, price, categories, created_at, description, image_url, second_id, title, creator_id, game_id
) VALUES (
             'b1a2c3d4-e5f6-7890-abcd-ef1234567890',
             120.00,
             'RPG',
             '2025-05-05',
             'Совместное прохождение основных боссов, получение достижений и трофеев.',
             'https://example.com/images/eldenring_coop.jpg',
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
