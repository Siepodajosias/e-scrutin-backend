CREATE SEQUENCE zone_id_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE zone
(
    id INT8 PRIMARY KEY,
    designation varchar(200) NOT NULL,
    code varchar(30) NOT NULL,
    type_zone_id INT8 NOT NULL,
    zone_id INT8,
    login_superviseur VARCHAR(255),
	create_by VARCHAR(100) NOT NULL,
	create_at TIMESTAMP NOT NULL,
	update_by VARCHAR(100),
	update_at TIMESTAMP,
	version INTEGER DEFAULT 0,
	adresse_mac VARCHAR(30),
	CONSTRAINT zone_type_zone_fk FOREIGN KEY (type_zone_id) REFERENCES type_zone(id),
	CONSTRAINT zone_parent_fk FOREIGN KEY (zone_id) REFERENCES zone(id),
    CONSTRAINT zone_uk UNIQUE (code, zone_id)
);
