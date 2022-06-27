
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
    id   INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name VARCHAR NOT NULL,
    year INTEGER NOT NULL
);

CREATE TABLE book
(
    id        INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    title     VARCHAR(100) NOT NULL,
    author    VARCHAR(100) NOT NULL,
    year      INTEGER      NOT NULL,
    user_id INTEGER REFERENCES users (id) ON DELETE SET NULL
);


