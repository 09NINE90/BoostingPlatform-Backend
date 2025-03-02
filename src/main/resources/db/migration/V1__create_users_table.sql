CREATE TABLE IF NOT EXISTS users (
    id          uuid            NOT NULL,
    "password"  varchar(255)    NULL,
    roles       varchar(255)    NULL,
    username    varchar(255)    NULL,
    CONSTRAINT user_pkey PRIMARY KEY (id)
);