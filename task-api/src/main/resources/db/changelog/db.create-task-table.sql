--liquibase formatted sql

--changeset artem:2
CREATE TABLE IF NOT EXISTS TASKS
(
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(128) NOT NULL,
    description VARCHAR(128),
    status VARCHAR(64) NOT NULL,
    done_at TIMESTAMP,
    owner_id BIGINT REFERENCES users(id)
)

--changeset artem:3
INSERT INTO tasks (title, description, status, owner_id)
VALUES ('Написать API для задач', 'Реализовать CRUD операции', 'IN_PROGRESS', 7);