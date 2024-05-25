-- NOT NULL constraint unavailable due to
-- inevitable null values in different child entities
-- Преимущества:
-- 1) Быстрый запрос: один SELECT для всех типов
--
-- Недостатки:
-- 1) Невозможность установить NOT NULL constraint вследствие того,
--    что разные дочерние классы имеют непересекающиеся наборы полей.
--
-- 2) Таблица нарушает 3 нормальную форму (+-)
--    Плохая структура данных.
DROP TABLE people;

CREATE TABLE people
(
    id                  SERIAL PRIMARY KEY,
    child_type          VARCHAR(10),
    first_name          VARCHAR(25),
    last_name           VARCHAR(25),
    prog_language       VARCHAR(25),
    project             VARCHAR(25),
    amount_of_employees INT
);
---------------------------------------------

TRUNCATE TABLE people RESTART IDENTITY;

SELECT *
FROM people;