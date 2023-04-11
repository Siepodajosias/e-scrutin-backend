CREATE SEQUENCE lieu_vote_id_seq START WITH 1 INCREMENT BY 50;
CREATE TABLE lieu_vote
(
    id INT8 PRIMARY KEY,
    code varchar(30) UNIQUE NULL,
	designation varchar(200) NOT NULL,
	commission_locale_id INT8 NOT NULL,
	create_by VARCHAR(100) NOT NULL,
	create_at TIMESTAMP NOT NULL,
	update_by VARCHAR(100),
	update_at TIMESTAMP,
	version INTEGER DEFAULT 0,
	adresse_mac VARCHAR(30),
	CONSTRAINT lieu_vote_commission_locale_fk FOREIGN KEY (commission_locale_id) REFERENCES commission_locale(id),
	CONSTRAINT lieu_vote_uk UNIQUE (code, designation, commission_locale_id)
);

ALTER TABLE lieu_vote ALTER COLUMN id SET DEFAULT nextval('lieu_vote_id_seq');
