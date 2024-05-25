DROP TABLE books;

CREATE TABLE books
(
    id      SERIAL PRIMARY KEY,
    user_id INT REFERENCES users (id),
    title   VARCHAR(50) NOT NULL
);
------------------------------------------------
TRUNCATE TABLE books RESTART IDENTITY;

INSERT INTO books (user_id, title)
VALUES (1, 'An Acceptable Time'),
       (1, 'Paths of Glory'),
       (1, 'A Monstrous Regiment of Women'),
       (2, 'A Time of Gifts'),
       (3, 'Specimen Days'),
       (3, 'The Soldier''s Art'),
       (3, 'Shall not Perish'),
       (4, 'Absalom, Absalom!'),
       (4, 'The Last Temptation'),
       (4, 'The Skull Beneath the Skin'),
       (5, 'The Violent Bear It Away'),
       (5, 'That Hideous Strength'),
       (6, 'Everything is Illuminated'),
       (6, 'Blood''s a Rover'),
       (7, 'East of Eden'),
       (7, 'The Skull Beneath the Skin'),
       (7, 'Now Sleeps the Crimson Petal'),
       (8, 'To Your Scattered Bodies Go'),
       (9, 'Bury My Heart at Wounded Knee'),
       (9, 'I Will Fear No Evil'),
       (9, 'Behold the Man'),
       (9, 'Everything is Illuminated'),
       (10, 'A Swiftly Tilting Planet'),
       (10, 'The Mirror Crack''d from Side to Side');

SELECT *
FROM books;