DROP SEQUENCE IF EXISTS utilisateur_id_seq;
CREATE SEQUENCE utilisateur_id_seq START WITH 10 INCREMENT BY 50;
ALTER TABLE utilisateur ALTER COLUMN id SET DEFAULT nextval('utilisateur_id_seq');