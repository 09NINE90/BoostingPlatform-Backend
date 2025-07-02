UPDATE users
SET roles   = 'ROLE_BOOSTER',
    enabled = true
WHERE username = 'alrazumov6@gmail.com';

UPDATE user_profile
SET image_url = 'https://i.postimg.cc/QxVHjTSh/photo-2025-05-28-19-01-05.jpg'
WHERE id = '94462720-bf3a-4535-ade5-99933af89c04'::uuid;

INSERT INTO game_tags (id, booster_profile_id, game_id, verified_by_admin_id, verified_by_admin, is_active,
                       verified_at)
VALUES ('6a4236d5-8ec5-49b8-979d-9e9d409f1ff7'::uuid, '7c21808e-a4c8-41a4-8659-84b3d2d58202'::uuid,
        '3e8e1d1a-5a19-4c6e-b872-7387a1a91e01'::uuid, '79c1d1cd-0be9-4f98-a6be-8974edad3cde'::uuid, true, true,
        '2025-05-03 18:30:52.000'),
       ('7c21808e-a4c8-41a4-8659-84b3d2d58444'::uuid, '7c21808e-a4c8-41a4-8659-84b3d2d58202'::uuid,
        'e2bc2767-7d71-4b3a-90a6-3db49b9d6542'::uuid, '79c1d1cd-0be9-4f98-a6be-8974edad3cde'::uuid, true, true,
        '2025-05-03 18:30:52.000');
