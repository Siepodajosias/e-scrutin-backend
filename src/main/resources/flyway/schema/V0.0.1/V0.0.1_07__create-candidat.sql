CREATE SEQUENCE candidat_id_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE candidat
(
    id INT8 PRIMARY KEY,
	nom VARCHAR(100) NULL,
    parti_politique_id INT8 NOT NULL,
    ordre INT4 NOT NULL,
	create_by VARCHAR(100) NOT NULL,
	create_at TIMESTAMP NOT NULL,
	update_by VARCHAR(100),
	update_at TIMESTAMP,
	version INTEGER DEFAULT 0,
	adresse_mac VARCHAR(30),
	CONSTRAINT candidat_parti_politique_fk FOREIGN KEY (parti_politique_id) REFERENCES parti_politique(id),
	    CONSTRAINT candidat_uk UNIQUE (nom, parti_politique_id)
);

ALTER TABLE candidat ALTER COLUMN id SET DEFAULT nextval('candidat_id_seq');