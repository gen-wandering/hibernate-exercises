TRUNCATE TABLE purchases RESTART IDENTITY;

INSERT INTO purchases (purchase_hash, purchase_sum)
VALUES ('1111111111111111111aaaa', 1111.11),
       ('22222222bbbbbbbbb222222', 2222.22),
       ('3cccccc33333c3ccccccccc', 3333.33),
       ('4ddddddd444dddddddddddd', 4444.44),
       ('5eeeeeeeeeeeeeeeeeeeee5', 5555.55),
       ('6ffffffffffffffffffffff', 6666.66),
       ('gggggggggggggggggggggg7', 7777.77);

SELECT *
FROM purchases;