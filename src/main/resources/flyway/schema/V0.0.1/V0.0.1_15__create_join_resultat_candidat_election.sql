CREATE SEQUENCE join_resultat_candidat_election_id_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE join_resultat_candidat_election
(
    id INT8 PRIMARY KEY,
    candidat_id INT8 NOT NULL,
    resultat_id INT8 NOT NULL,
    voix INT8 NULL,
	create_by VARCHAR(100) NOT NULL,
	create_at TIMESTAMP NOT NULL,
	update_by VARCHAR(100),
	update_at TIMESTAMP,
	version INTEGER DEFAULT 0,
	adresse_mac VARCHAR(30),
	CONSTRAINT join_resultat_candidat_election_candidat_fk FOREIGN KEY (candidat_id) REFERENCES candidat(id),
    CONSTRAINT join_resultat_candidat_election_resultat_fk FOREIGN KEY (resultat_id) REFERENCES resultat(id),
    CONSTRAINT join_resultat_candidat_election_uk UNIQUE (candidat_id, resultat_id)
);
