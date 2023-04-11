CREATE SEQUENCE bureau_vote_id_seq START WITH 1 INCREMENT BY 50;
CREATE TABLE bureau_vote
(
    id INT8 PRIMARY KEY,
    code varchar(30) UNIQUE NULL,
	designation varchar(200) NOT NULL,
	lieu_vote_id INT8 NOT NULL,
	zone_id INT8 NOT NULL,
	create_by VARCHAR(100) NOT NULL,
	create_at TIMESTAMP NOT NULL,
	update_by VARCHAR(100),
	update_at TIMESTAMP,
	version INTEGER DEFAULT 0,
	adresse_mac VARCHAR(30),
	CONSTRAINT bureau_vote_lieu_vote_fk FOREIGN KEY (lieu_vote_id) REFERENCES lieu_vote(id),
	constraint bureau_vote_zone_fk foreign key (zone_id) references zone(id)
);

ALTER TABLE bureau_vote ALTER COLUMN id SET DEFAULT nextval('bureau_vote_id_seq');
