CREATE TABLE home_carousel
(
    id          INT8 GENERATED BY DEFAULT AS IDENTITY ( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    title       VARCHAR(255),
    description TEXT,
    image_url   TEXT,
    is_active   BOOLEAN,
    CONSTRAINT home_carousel_pkey PRIMARY KEY (id)

);

INSERT INTO home_carousel (title, description, image_url, is_active)
VALUES ('It is Destiny 2!', 'Discounts up to 50% on all summer collection items',
        'https://avatars.mds.yandex.net/i?id=a5a9836f80838412a651618b6c5d84fa7e1cb38e-5313698-images-thumbs&n=13',
        true),
       ('It is Call of duty black ops 6!', 'Discover the new arrivals this season',
        'https://avatars.mds.yandex.net/i?id=431dc44869f39f47db51853ca8836578e0ef1ddf-7546005-images-thumbs&n=13',
        true),
       ('It is League legends!', 'This week only - exclusive purchase conditions',
        'https://avatars.mds.yandex.net/i?id=65a617910a263124cb205d7acf17563332741060-5240721-images-thumbs&n=13',
        false),
       ('It is CS2!', null,
        'https://avatars.mds.yandex.net/i?id=a6bb09ef114d3118366b8b152b7f5d692f31f0c0-6598906-images-thumbs&n=13',
        true),
       ('It is League legends 3!', 'This week only - NOT exclusive purchase conditions',
        'https://avatars.mds.yandex.net/i?id=65a617910a263124cb205d7acf17563332741060-5240721-images-thumbs&n=13',
        true);