TRUNCATE TABLE comments, posts, authors RESTART IDENTITY CASCADE;

INSERT INTO authors (username)
VALUES ('Lawson McClain'),
       ('Terry Beil'),
       ('Ellis Bauer'),
       ('Peter O’Connell'),
       ('Riley Green'),
       ('Palmer Caldwell'),
       ('Celia Kaur');

INSERT INTO posts (subject, author_id)
VALUES ('Hidden talents', 2),
       ('Pets', 5),
       ('Music', 6),
       ('Books', 1);

INSERT INTO comments (reply, author_id, post_id)
VALUES ('medium', 1, 1),
       ('high', 1, 2),
       ('low', 1, 3),
       ('low', 2, 2),
       ('high', 2, 3),
       ('medium', 2, 4),
       ('medium', 3, 2),
       ('high', 3, 3),
       ('low', 4, 1),
       ('medium', 4, 2),
       ('low', 4, 4),
       ('high', 5, 3),
       ('medium ', 5, 4),
       ('high', 6, 1),
       ('high', 6, 2),
       ('low', 6, 4),
       ('low', 7, 1),
       ('medium ', 7, 2),
       ('high', 7, 3),
       ('medium', 7, 4);

SELECT *
FROM authors;

SELECT *
FROM posts;

SELECT *
FROM comments;

-- Authors with their posts
SELECT *
FROM authors a
         LEFT JOIN posts p ON a.id = p.author_id
ORDER BY p.author_id NULLS LAST, a.id;

-- Authors with their posts and comments on it from other authors
SELECT a.id,
       a.username AS authorname,
       p.subject,
       c.reply,
       u.username AS commentname,
       u.id AS commentid
FROM authors a
         JOIN posts p ON a.id = p.author_id
         JOIN comments c ON c.post_id = p.id
         JOIN authors u ON c.author_id = u.id
ORDER BY a.id;