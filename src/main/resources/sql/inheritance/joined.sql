-- Преимущества:
-- 1) Полностью соответствует нормальным формам.
--
-- Недостатки:
-- 1) Медленные запросы через JOIN на каждую из дочерних
--    сущностей.

DROP TABLE people, programmers, managers, ceo;

CREATE TABLE people
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(25),
    last_name  VARCHAR(25)
);

CREATE TABLE programmers
(
    id            INT PRIMARY KEY REFERENCES people (id),
    prog_language VARCHAR(25)
);

CREATE TABLE managers
(
    id      INT PRIMARY KEY REFERENCES people (id),
    project VARCHAR(25)
);

CREATE TABLE ceo
(
    id                  INT PRIMARY KEY REFERENCES managers (id),
    amount_of_employees INT
);
---------------------------------------------------------------

TRUNCATE TABLE people, programmers, managers, ceo RESTART IDENTITY CASCADE;

SELECT *
FROM people;

SELECT *
FROM managers;

SELECT *
FROM programmers;

SELECT *
FROM ceo;