-- Вставляем секции для предложений
INSERT INTO offer_section (id, offer_id, title, type, description)
VALUES
    -- Секции для Premium Boost
    ('d3eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', 'f6e1a183-0b6d-4f94-9e33-7a1b8e46050f', 'What you will get', 'LIST', 'Benefits included in this boost'),
    ('e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', 'f6e1a183-0b6d-4f94-9e33-7a1b8e46050f', 'Additional options', 'BLOCK', 'Extra services you can add'),
    ('f5eebc99-9c0b-4ef8-bb6d-6bb9bd380a16', 'f6e1a183-0b6d-4f94-9e33-7a1b8e46050f', 'Related offers', 'RELATED_OFFERS', NULL),

    -- Секции для Starter Leveling
    ('06eebc99-9c0b-4ef8-bb6d-6bb9bd380a17', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Leveling details', 'LIST', 'What includes in leveling service'),
    ('17eebc99-9c0b-4ef8-bb6d-6bb9bd380a18', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'FAQ', 'FAQ', 'Frequently asked questions'),

    -- Секции для Mythic Raid Boost
    ('28eebc99-9c0b-4ef8-bb6d-6bb9bd380a19', 'ae9b14d1-1a86-4622-9760-eae403760917', 'Raid rewards', 'LIST', 'Loot you will receive'),
    ('39eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', 'ae9b14d1-1a86-4622-9760-eae403760917', 'Requirements', 'BLOCK', 'What you need to participate');

-- Вставляем элементы для секций
INSERT INTO offer_section_item (title, type, description, related_offer_id, image_url, price, parent_id, section_id)
VALUES
    -- Элементы для "What you will get" (LIST)
    (NULL, 'SIMPLE', 'Premium account status for 30 days', NULL, NULL, NULL, NULL, 'd3eebc99-9c0b-4ef8-bb6d-6bb9bd380a14'),
    (NULL, 'SIMPLE', 'Exclusive in-game title', NULL, NULL, NULL, NULL, 'd3eebc99-9c0b-4ef8-bb6d-6bb9bd380a14'),
    (NULL, 'SIMPLE', '5000 gold coins', NULL, NULL, NULL, NULL, 'd3eebc99-9c0b-4ef8-bb6d-6bb9bd380a14'),

    -- Элементы для "Additional options" (BLOCK)
    ('Gear packs include', 'ACCORDION_LIST', NULL, NULL, NULL, NULL, NULL, 'e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a15'),
    ('Requirements', 'ACCORDION_LIST', NULL, NULL, NULL, NULL, NULL, 'e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a15'),

    -- Вложенные элементы для "Gear packs include"
    ('Warrior Set', 'SIMPLE', 'Full set of warrior gear', NULL, NULL, 19.99, 1, 'e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a15'),
    ('Mage Set', 'SIMPLE', 'Full set of mage gear', NULL, NULL, 19.99, 1, 'e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a15'),

    -- Вложенные элементы для "Requirements"
    ('Account', 'SIMPLE', 'Active subscription required', NULL, NULL, NULL, 2, 'e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a15'),
    ('Level', 'SIMPLE', 'Minimum level 60 character', NULL, NULL, NULL, 2, 'e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a15'),

    -- Элементы для "Related offers" (RELATED_OFFERS)
    ('Mythic Raid Boost', 'RELATED_OFFER', 'Complete hardest raid content', 'ae9b14d1-1a86-4622-9760-eae403760917', '/images/raid1.jpg', 99.99, NULL, 'f5eebc99-9c0b-4ef8-bb6d-6bb9bd380a16'),
    ('Starter Leveling', 'RELATED_OFFER', 'Level up quickly', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', '/images/leveling1.jpg', 29.99, NULL, 'f5eebc99-9c0b-4ef8-bb6d-6bb9bd380a16'),

    -- Элементы для "Leveling details" (Starter Leveling)
    (NULL, 'SIMPLE', '1-60 leveling in 24 hours', NULL, NULL, NULL, NULL, '06eebc99-9c0b-4ef8-bb6d-6bb9bd380a17'),
    (NULL, 'SIMPLE', 'Optimal gear for your level', NULL, NULL, NULL, NULL, '06eebc99-9c0b-4ef8-bb6d-6bb9bd380a17'),

    -- Элементы для FAQ (Starter Leveling)
    ('How long does it take?', 'ACCORDION', 'Usually takes 24-48 hours depending on class', NULL, NULL, NULL, NULL, '17eebc99-9c0b-4ef8-bb6d-6bb9bd380a18'),
    ('Is it safe?', 'ACCORDION', '100% safe with our professional boosters', NULL, NULL, NULL, NULL, '17eebc99-9c0b-4ef8-bb6d-6bb9bd380a18'),

    -- Элементы для "Raid rewards" (Mythic Raid Boost)
    (NULL, 'SIMPLE', 'Mythic quality gear', NULL, NULL, NULL, NULL, '28eebc99-9c0b-4ef8-bb6d-6bb9bd380a19'),
    (NULL, 'SIMPLE', 'Exclusive mount', NULL, NULL, NULL, NULL, '28eebc99-9c0b-4ef8-bb6d-6bb9bd380a19'),

    -- Элементы для "Requirements" (Mythic Raid Boost)
    ('Item Level', 'SIMPLE', 'Minimum 240 item level', NULL, NULL, NULL, NULL, '39eebc99-9c0b-4ef8-bb6d-6bb9bd380a20'),
    ('Consumables', 'SIMPLE', 'Bring your own flasks and potions', NULL, NULL, NULL, NULL, '39eebc99-9c0b-4ef8-bb6d-6bb9bd380a20');