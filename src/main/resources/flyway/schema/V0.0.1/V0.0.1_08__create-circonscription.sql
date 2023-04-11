
CREATE SEQUENCE circonscription_id_seq START WITH 1 INCREMENT BY 50;
CREATE TABLE circonscription
(
    id INT8 PRIMARY KEY,
    code varchar(30) NULL,
	designation varchar(200) NOT NULL,
	create_by VARCHAR(100) NOT NULL,
	create_at TIMESTAMP NOT NULL,
	update_by VARCHAR(100),
	update_at TIMESTAMP,
	version INTEGER DEFAULT 0,
	adresse_mac VARCHAR(30),
    CONSTRAINT circonscription_uk UNIQUE (code, designation)
);

ALTER TABLE circonscription ALTER COLUMN id SET DEFAULT nextval('circonscription_id_seq');
