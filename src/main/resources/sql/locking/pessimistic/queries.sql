TRUNCATE TABLE payment RESTART IDENTITY;

INSERT INTO payment (amount, product)
VALUES (1111.11, 'Books'),
       (2222.22, 'Music'),
       (3333.33, 'Clothes');

SELECT *
FROM payment;
