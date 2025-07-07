CREATE TABLE IF NOT EXISTS chat_room
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title      VARCHAR(255)             NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS chat_room_participants
(
    chat_room_id    UUID NOT NULL,
    participants_id UUID NOT NULL,
    PRIMARY KEY (chat_room_id, participants_id),
    FOREIGN KEY (chat_room_id) REFERENCES chat_room (id) ON DELETE CASCADE,
    FOREIGN KEY (participants_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS chat_message
(
    id           UUID PRIMARY KEY DEFAULT  gen_random_uuid(),
    text         TEXT                     NOT NULL,
    created_at   TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    chat_room_id UUID                     NOT NULL,
    sender_id    UUID                     NOT NULL,
    FOREIGN KEY (chat_room_id) REFERENCES chat_room (id) ON DELETE CASCADE,
    FOREIGN KEY (sender_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE INDEX idx_chat_message_room_created_at ON chat_message(chat_room_id, created_at);

INSERT INTO chat_room (id, title, created_at)
VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'General Chat', CURRENT_TIMESTAMP);

INSERT INTO chat_room_participants (chat_room_id, participants_id)
VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '5c21809e-a4c8-49a4-8659-86b3d2d58201');

INSERT INTO chat_message (id, text, created_at, chat_room_id, sender_id)
VALUES ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'Hello everyone!', CURRENT_TIMESTAMP,
        'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '5c21809e-a4c8-49a4-8659-86b3d2d58201');