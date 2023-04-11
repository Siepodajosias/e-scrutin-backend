CREATE SEQUENCE election_id_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE election
(
    id INT8 PRIMARY KEY,
    code VARCHAR(100) UNIQUE NOT NULL,
    type VARCHAR(100) DEFAULT 'MUNICIPALE',
    annee INT8 DEFAULT 2023,
    tour varchar(255) NOT NULL,
	create_by VARCHAR(100) NOT NULL,
	create_at TIMESTAMP NOT NULL,
	update_by VARCHAR(100),
	update_at TIMESTAMP,
	version INTEGER DEFAULT 0,
	adresse_mac VARCHAR(30),
    CONSTRAINT election_uk UNIQUE (type, annee, tour)
);

 ALTER TABLE election ALTER COLUMN id SET DEFAULT nextval('election_id_seq');

