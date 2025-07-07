ALTER TABLE chat_room
    ADD COLUMN order_id UUID UNIQUE;

ALTER TABLE chat_room
    ADD CONSTRAINT fk_chatroom_order
        FOREIGN KEY (order_id)
            REFERENCES orders (id);
