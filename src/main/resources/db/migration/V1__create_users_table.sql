CREATE TABLE IF NOT EXISTS users (
    id                  uuid PRIMARY KEY,
    "password"          varchar(255),
    roles               varchar(255),
    username            varchar(255) UNIQUE,
    enabled             boolean DEFAULT false NOT NULL,
    confirmation_token  text
);
