CREATE TABLE entrepot_donnee(
    id INT8 PRIMARY KEY,
    annee INT8,
    type_election varchar(100),
    code_commune varchar (255) not null,
    designation_commune varchar (255) not null,
    code_commission_locale varchar (255) not null,
    designation_commission_locale varchar (255) not null,
    code_lieu_vote varchar (255) not null ,
    designation_lieu_vote varchar (255) not null,
    code_bureau_vote varchar (255) not null,
    nombre_inscrits INTEGER,
    homme_votants INTEGER,
    femme_votantes INTEGER,
    total_votants INTEGER,
    taux_participation NUMERIC(19,2),
    nombre_bulletin_nuls INTEGER,
    nombre_bulletin_blancs INTEGER,
    suffrage_exprimes INTEGER,
    score_1 INTEGER,
    score_2 INTEGER,
    score_3 INTEGER,
    score_4 INTEGER,
    score_5 INTEGER,
    score_6 INTEGER,
    score_7 INTEGER,
    score_8 INTEGER,
    score_9 INTEGER,
    score_10 INTEGER,
    score_11 INTEGER,
    score_12 INTEGER,
    score_13 INTEGER,
    score_14 INTEGER,
    score_15 INTEGER,
    synchro boolean default false,
    create_by VARCHAR(100) NOT NULL,
    create_at TIMESTAMP NOT NULL,
    nom_fcihier varchar (255) not null
);

CREATE SEQUENCE entrepot_donnee_id_seq START WITH 1 INCREMENT BY 50;
ALTER TABLE entrepot_donnee ALTER COLUMN id SET DEFAULT nextval('entrepot_donnee_id_seq');

CREATE INDEX code_commune_index ON entrepot_donnee(code_commune);
CREATE INDEX code_commission_locale_index ON entrepot_donnee(code_commission_locale);
CREATE INDEX code_bureau_vote_index ON entrepot_donnee(code_bureau_vote);
CREATE INDEX code_lieu_vote_index ON entrepot_donnee(code_lieu_vote);