DROP TABLE authors, posts, comments;

CREATE TABLE authors
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(25) UNIQUE
);

CREATE TABLE posts
(
    id        SERIAL PRIMARY KEY,
    subject   VARCHAR(90),
    author_id INT REFERENCES authors (id)
);

CREATE TABLE comments
(
    id        SERIAL PRIMARY KEY,
    reply     VARCHAR(256),
    author_id INT REFERENCES authors (id),
    post_id   INT REFERENCES posts (id)
);