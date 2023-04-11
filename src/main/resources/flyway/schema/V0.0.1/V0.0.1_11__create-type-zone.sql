CREATE SEQUENCE type_zone_id_seq START WITH 1 INCREMENT BY 50;
CREATE TABLE type_zone
(
    id INT8 PRIMARY KEY,
    code varchar(30) NULL UNIQUE,
	designation varchar(200) NOT NULL,
	create_by VARCHAR(100) NOT NULL,
	create_at TIMESTAMP NOT NULL,
	update_by VARCHAR(100),
	update_at TIMESTAMP,
	version INTEGER DEFAULT 0,
	adresse_mac VARCHAR(30)
);
