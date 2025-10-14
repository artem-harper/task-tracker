--liquibase formatted sql

--changeset artem:1
CREATE TABLE IF NOT EXISTS USERS(
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(128) UNIQUE NOT NULL,
    password VARCHAR(128) NOT NULL
)