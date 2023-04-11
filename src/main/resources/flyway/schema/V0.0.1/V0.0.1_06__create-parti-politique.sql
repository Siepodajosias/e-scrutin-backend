CREATE SEQUENCE parti_politique_id_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE parti_politique
(
    id INT8 PRIMARY KEY,
    nom VARCHAR(250) UNIQUE NOT NULL,
    code_couleur varchar(100) unique,
	create_by VARCHAR(100) NOT NULL,
	create_at TIMESTAMP NOT NULL,
	update_by VARCHAR(100),
	update_at TIMESTAMP,
	version INTEGER DEFAULT 0,
	adresse_mac VARCHAR(30)
);

ALTER TABLE parti_politique ALTER COLUMN id SET DEFAULT nextval('parti_politique_id_seq');
