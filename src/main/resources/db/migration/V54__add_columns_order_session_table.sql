ALTER TABLE order_session
    ADD COLUMN progress_message TEXT DEFAULT NULL,
    ADD COLUMN imgur_link       TEXT DEFAULT NULL;