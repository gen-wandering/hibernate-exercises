DROP TABLE payment;

CREATE TABLE payment
(
    id      SERIAL PRIMARY KEY,
    amount  NUMERIC,
    product VARCHAR(30)
)