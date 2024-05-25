-- Преимущества:
--
-- Недостатки:
-- 1) Сложные запросы через UNION на каждую из дочерних
--    сущностей.
--
-- 2) Дублирование строк.

DROP TABLE people, programmers, managers;
DROP SEQUENCE people_id_seq;

CREATE TABLE people
(
    id         INT PRIMARY KEY,
    first_name VARCHAR(25),
    last_name  VARCHAR(25)
);

CREATE TABLE programmers
(
    id            INT PRIMARY KEY,
    first_name    VARCHAR(25),
    last_name     VARCHAR(25),
    prog_language VARCHAR(25)
);

CREATE TABLE managers
(
    id         INT PRIMARY KEY,
    first_name VARCHAR(25),
    last_name  VARCHAR(25),
    project    VARCHAR(25)
);

CREATE SEQUENCE people_id_seq;
--------------------------------------------------

TRUNCATE TABLE people, programmers, managers;
ALTER SEQUENCE people_id_seq RESTART;

SELECT *
FROM people;

SELECT *
FROM managers;

SELECT *
FROM programmers;