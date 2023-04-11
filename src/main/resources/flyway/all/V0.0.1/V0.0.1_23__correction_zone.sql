
-- Ajout de la sous prefecture AGNIBILEKROU
INSERT INTO public."zone" (id, code, designation, type_zone_id, zone_id, login_superviseur, create_by, create_at, update_by, update_at, "version", adresse_mac)
VALUES(nextval('zone_id_seq'), '21101', 'AGNIBILEKROU', 11, (select z.id from zone z where z.designation = 'AGNIBILEKROU' and z.type_zone_id = 31), NULL, 'RDD', '2023-02-25 21:40:31.624', NULL, NULL, 0, NULL);

-- Ajout de la sous prefecture KOUASSI-KOUASSIKRO
INSERT INTO public."zone" (id, code, designation, type_zone_id, zone_id, login_superviseur, create_by, create_at, update_by, update_at, "version", adresse_mac)
VALUES(nextval('zone_id_seq'), '21151', 'KOUASSI-KOUASSIKRO', 11, (select z.id from zone z where z.designation = 'KOUASSI-KOUASSIKRO' and z.type_zone_id = 31), NULL, 'RDD', '2023-02-25 21:40:31.624', NULL, NULL, 0, NULL);

-- Ajout de la sous prefecture TAFIRE
INSERT INTO public."zone" (id, code, designation, type_zone_id, zone_id, login_superviseur, create_by, create_at, update_by, update_at, "version", adresse_mac)
VALUES(nextval('zone_id_seq'), '21201', 'TAFIRE', 11, (select z.id from zone z where z.designation = 'NIAKARAMADOUGOU' and z.type_zone_id = 31), NULL, 'RDD', '2023-02-25 21:40:31.624', NULL, NULL, 0, NULL);

-- Ajout de la sous prefecture TORTIYA
INSERT INTO public."zone" (id, code, designation, type_zone_id, zone_id, login_superviseur, create_by, create_at, update_by, update_at, "version", adresse_mac)
VALUES(nextval('zone_id_seq'), '21251', 'TORTIYA', 11, (select z.id from zone z where z.designation = 'NIAKARAMADOUGOU' and z.type_zone_id = 31), NULL, 'RDD', '2023-02-25 21:40:31.624', NULL, NULL, 0, NULL);

-- Ajout de la sous prefecture MAYO
INSERT INTO public."zone" (id, code, designation, type_zone_id, zone_id, login_superviseur, create_by, create_at, update_by, update_at, "version", adresse_mac)
VALUES(nextval('zone_id_seq'), '20601', 'MAYO', 11, (select z.id from zone z where z.designation = 'SOUBRE ' and z.type_zone_id = 31), NULL, 'RDD', '2023-02-25 21:40:31.624', NULL, NULL, 0, NULL);

-- Ajout de la sous prefecture NAPIELEDOUGOU
INSERT INTO public."zone" (id, code, designation, type_zone_id, zone_id, login_superviseur, create_by, create_at, update_by, update_at, "version", adresse_mac)
VALUES(nextval('zone_id_seq'), '20751', 'NAPIELEDOUGOU', 11, (select z.id from zone z where z.designation = 'KORHOGO' and z.type_zone_id = 31), NULL, 'RDD', '2023-02-25 21:40:31.624', NULL, NULL, 0, NULL);

-- Ajout de la sous prefecture NIAKARAMADOUGOU
INSERT INTO public."zone" (id, code, designation, type_zone_id, zone_id, login_superviseur, create_by, create_at, update_by, update_at, "version", adresse_mac)
VALUES(nextval('zone_id_seq'), '20951', 'NIAKARAMADOUGOU', 11, (select z.id from zone z where z.designation = 'NIAKARAMADOUGOU' and z.type_zone_id = 31), NULL, 'RDD', '2023-02-25 21:40:31.291', NULL, NULL, 0, NULL);

-- Ajout de la zone commune MAYO
INSERT INTO public."zone" (id, code, designation, type_zone_id, zone_id, login_superviseur, create_by, create_at, update_by, update_at, "version", adresse_mac)
VALUES(nextval('zone_id_seq'), '18601', 'MAYO', 21, (select z.id from zone z where z.designation = 'MAYO' and z.type_zone_id = 11), NULL, 'RDD', '2023-02-25 21:40:31.624', NULL, NULL, 0, NULL);

-- Ajout de la commune GBELEBAN
INSERT INTO public."zone" (id, code, designation, type_zone_id, zone_id, login_superviseur, create_by, create_at, update_by, update_at, "version", adresse_mac)
VALUES(nextval('zone_id_seq'), '20701', 'GBELEBAN', 21, (select z.id from zone z where z.designation = 'GBELEBAN' and z.type_zone_id = 11), NULL, 'RDD', '2023-02-25 21:30:43.021', NULL, NULL, 0, NULL);

-- Ajout de la commune NAPIELEDOUGOU
INSERT INTO public."zone" (id, code, designation, type_zone_id, zone_id, login_superviseur, create_by, create_at, update_by, update_at, "version", adresse_mac)
VALUES(nextval('zone_id_seq'), '17251', 'NAPIELEDOUGOU', 21, (select z.id from zone z where z.designation = 'NAPIELEDOUGOU' and z.type_zone_id = 11), NULL, 'RDD', '2023-02-25 21:30:43.021', NULL, NULL, 0, NULL);

-- Correction commune MBENGUE
UPDATE public."zone" SET designation='MBENGUE', zone_id = (select z.id from zone z where z.designation = 'M''BENGUE' and z.type_zone_id = 11) where designation = 'M''BENGUE' and type_zone_id = 21;

-- Correction de la COMMUNE KOUASSI-KOUASSIKRO
UPDATE public."zone" SET zone_id = (select z.id from zone z where z.designation = 'KOUASSI-KOUASSIKRO' and z.type_zone_id = 11) where designation = 'KOUASSI KOUASSIKRO' and type_zone_id = 21;

-- Correction de la COMMUNE TAFIRE
UPDATE public."zone" SET zone_id = (select z.id from zone z where z.designation = 'TAFIRE' and z.type_zone_id = 11) where designation = 'TAFIRE' and type_zone_id = 21;

-- Correction de la COMMUNE TORTIYA
UPDATE public."zone" SET zone_id = (select z.id from zone z where z.designation = 'TORTIYA' and z.type_zone_id = 11) where designation = 'TORTIYA' and type_zone_id = 21;


-- Ajout de la commune ASSINIE-MAFIA
INSERT INTO public."zone" (id, code, designation, type_zone_id, zone_id, login_superviseur, create_by, create_at, update_by, update_at, "version", adresse_mac)
VALUES(nextval('zone_id_seq'), '20851', 'ASSINIE-MAFIA', 21, (select z.id from zone z where z.designation = 'ASSINIE-MAFIA' and z.type_zone_id = 11), NULL, 'RDD', '2023-02-25 21:40:31.418', NULL, NULL, 0, NULL);

-- Ajout de la commune NIAKARAMADOUGOU
INSERT INTO public."zone" (id, code, designation, type_zone_id, zone_id, login_superviseur, create_by, create_at, update_by, update_at, "version", adresse_mac)
VALUES(nextval('zone_id_seq'), '21001', 'NIAKARAMADOUGOU', 21, (select z.id from zone z where z.designation = 'NIAKARAMADOUGOU' and z.type_zone_id = 11), NULL, 'RDD', '2023-02-25 21:40:31.291', NULL, NULL, 0, NULL);

-- Correction de la commune BIN HOUYE
UPDATE public."zone" SET designation='BIN HOUYE' WHERE designation = 'BIN-HOUYE' and type_zone_id = 21;

-- Correction de la commune TIE-N'DIEKRO
UPDATE public."zone" SET designation='TIE N''DIEKRO' WHERE designation = 'TIE-N''DIEKRO' and type_zone_id = 21;

-- Correction de la commune MAFERE
UPDATE public."zone" SET designation='MAFERE' WHERE designation = 'MAFFERE' and type_zone_id = 21;

-- Ajout de la commune N'DOUCI
INSERT INTO public."zone" (id, code, designation, type_zone_id, zone_id, login_superviseur, create_by, create_at, update_by, update_at, "version", adresse_mac)
VALUES(nextval('zone_id_seq'), '21051', 'N''DOUCI', 21, (select z.id from zone z where z.designation = 'N''DOUCI' and z.type_zone_id = 11), NULL, 'RDD', '2023-02-25 21:40:31.291', NULL, NULL, 0, NULL);

-- Ajout de la commune ATTIEGOUAKRO
INSERT INTO public."zone" (id, code, designation, type_zone_id, zone_id, login_superviseur, create_by, create_at, update_by, update_at, "version", adresse_mac)
VALUES(nextval('zone_id_seq'), '21251', 'ATTIEGOUAKRO', 21, (select z.id from zone z where z.designation = 'ATTIEGOUAKRO' and z.type_zone_id = 11), NULL, 'RDD', '2023-02-25 21:40:31.291', NULL, NULL, 0, NULL);
