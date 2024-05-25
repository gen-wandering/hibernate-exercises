DROP TABLE purchases;

CREATE TABLE purchases
(
    id            SERIAL PRIMARY KEY,
    purchase_hash VARCHAR(256),
    purchase_sum  NUMERIC,
    version       BIGINT DEFAULT 0
);