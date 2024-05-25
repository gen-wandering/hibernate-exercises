DROP TABLE users;

CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(25) NOT NULL,
    last_name  VARCHAR(25) NOT NULL,
    birth_date DATE        NOT NULL,
    user_role  VARCHAR(10) NOT NULL
);
-----------------------------------------------------------------

TRUNCATE TABLE users RESTART IDENTITY CASCADE;

INSERT INTO users (first_name, last_name, birth_date, user_role)
VALUES ('Quinton', 'Bins', '1994-12-21', 'USER'),
       ('Gustavo', 'Muller', '1994-03-16', 'ADMIN'),
       ('Shannan', 'Bahringer', '2001-04-24', 'MODERATOR'),
       ('Cory', 'Bechtelar', '1985-01-19', 'MODERATOR'),
       ('Lamont', 'Olson', '1984-07-23', 'USER'),
       ('Jacqulyn', 'Kohler', '2006-05-06', 'USER'),
       ('Jennine', 'Gerhold', '2003-11-14', 'USER'),
       ('Kirk', 'Leannon', '1992-02-18', 'USER'),
       ('Jose', 'Kulas', '1989-09-30', 'USER'),
       ('Fannie', 'Botsford', '1996-08-01', 'USER');

SELECT *
FROM users;

SELECT u.first_name, u.last_name, count(*) amount_of_books
FROM users u
         JOIN books b ON u.id = b.user_id
GROUP BY u.first_name, u.last_name
HAVING count(*) > 2
ORDER BY amount_of_books DESC;