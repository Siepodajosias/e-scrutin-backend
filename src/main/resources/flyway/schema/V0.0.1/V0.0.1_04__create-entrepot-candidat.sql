CREATE TABLE entrepot_donnee_candidat(
id INT8 PRIMARY KEY,
annee INT8,
type_election varchar(100),
code_commune varchar (255) not null,
designation_commune varchar (255) not null,
code_commission_locale varchar (255) not null,
designation_commission_locale varchar (255) not null,
parti_1 varchar (255),
candidat_1 varchar (255),
parti_2 varchar (255),
candidat_2 varchar (255),
parti_3 varchar (255),
candidat_3 varchar (255),
parti_4 varchar (255),
candidat_4 varchar (255),
parti_5 varchar (255),
candidat_5 varchar (255),
parti_6 varchar (255),
candidat_6 varchar (255),
parti_7 varchar (255),
candidat_7 varchar (255),
parti_8 varchar (255),
candidat_8 varchar (255),
parti_9 varchar (255),
candidat_9 varchar (255),
parti_10 varchar (255),
candidat_10 varchar (255),
parti_11 varchar (255),
candidat_11 varchar (255),
parti_12 varchar (255),
candidat_12 varchar (255),
parti_13 varchar (255),
candidat_13 varchar (255),
parti_14 varchar (255),
candidat_14 varchar (255),
parti_15 varchar (255),
candidat_15 varchar (255),
synchro boolean default false,
create_by VARCHAR(100) NOT NULL,
create_at TIMESTAMP NOT NULL,
nom_fcihier varchar (255) not null);

CREATE SEQUENCE entrepot_donnee_candidat_id_seq START WITH 1 INCREMENT BY 50;
ALTER TABLE entrepot_donnee_candidat ALTER COLUMN id SET DEFAULT nextval('entrepot_donnee_candidat_id_seq');