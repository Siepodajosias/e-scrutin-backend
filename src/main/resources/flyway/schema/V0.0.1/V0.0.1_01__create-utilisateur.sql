CREATE TABLE utilisateur
(
    id INT8 PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    nom VARCHAR(100) NOT NULL,
    prenoms VARCHAR(100) NOT NULL,
    role VARCHAR(100) NOT NULL
);
