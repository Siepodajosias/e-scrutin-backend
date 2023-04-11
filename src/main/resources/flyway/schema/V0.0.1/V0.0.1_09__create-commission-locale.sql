CREATE SEQUENCE commission_locale_id_seq START WITH 1 INCREMENT BY 50;
CREATE TABLE commission_locale
(
    id INT8 PRIMARY KEY,
    code varchar(30) NULL,
	designation varchar(200) NOT NULL,
	circonscription_id INT8 NOT NULL,
	login_responsable varchar(255),
	create_by VARCHAR(100) NOT NULL,
	create_at TIMESTAMP NOT NULL,
	update_by VARCHAR(100),
	update_at TIMESTAMP,
	version INTEGER DEFAULT 0,
	adresse_mac VARCHAR(30),
	CONSTRAINT commission_locale_circonscription_fk FOREIGN KEY (circonscription_id) REFERENCES circonscription(id),
	CONSTRAINT commission_locale_uk UNIQUE (code, designation, circonscription_id)
);

ALTER TABLE commission_locale ALTER COLUMN id SET DEFAULT nextval('commission_locale_id_seq');
