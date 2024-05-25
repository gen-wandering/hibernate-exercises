DROP TABLE images;
DROP TABLE profiles;

CREATE TABLE profiles
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(25) NOT NULL
);
CREATE TABLE images
(
    profile_id  INT REFERENCES profiles (id),
    image_index INT,
    image_name  VARCHAR(25) NOT NULL
);
------------------------------------------------
TRUNCATE TABLE images;

INSERT INTO profiles (username)
VALUES ('Sonny Zuniga'),
       ('Vern Fischer'),
       ('Demetrius Cantu'),
       ('Alyson Valenzuela');

SELECT *
FROM profiles;