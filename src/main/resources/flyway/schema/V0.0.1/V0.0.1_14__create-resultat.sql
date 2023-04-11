CREATE SEQUENCE resultat_id_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE resultat
(
    id INT8 PRIMARY KEY,
    bureau_vote_id INT8 NOT NULL,
     election_id INT8 NOT NULL,
    nombre_votants INT8 NULL,
    nombre_inscrits INT8 NULL,
    nombre_hommes_votants INT8 NULL,
    nombre_femmes_votantes INT8 NULL,
    nombre_bulletins_nuls INT8 NULL,
    Nombre_bulletins_blancs INT8 NULL,
    taux_participation decimal(18, 2) NULL,
    suffrages_exprimes INT8 null,
    heure_reception VARCHAR(100) NULL,
	create_by VARCHAR(100) NOT NULL,
	create_at TIMESTAMP NOT NULL,
	update_by VARCHAR(100),
	update_at TIMESTAMP,
	version INTEGER DEFAULT 0,
	adresse_mac VARCHAR(30),
	    CONSTRAINT resultat_election_fk FOREIGN KEY (election_id) REFERENCES election(id),
	CONSTRAINT resultat_bureau_vote_fk FOREIGN KEY (bureau_vote_id) REFERENCES bureau_vote(id)
);
