INSERT INTO utilisateur (username,"password",nom,prenoms,"role")
VALUES
('mhounye','$2a$10$Zm1ReWsMt2s9NeLd9HaAlOcT0x.k80CPIzDuAGNc8wPkN4nshVsKi','Hounyé','Myriam','ADMIN'),
('centrale','$2a$10$Zm1ReWsMt2s9NeLd9HaAlOcT0x.k80CPIzDuAGNc8wPkN4nshVsKi','Centrale','Dir.','DIRECTION_CENTRALE'),
('region','$2a$10$Zm1ReWsMt2s9NeLd9HaAlOcT0x.k80CPIzDuAGNc8wPkN4nshVsKi','Région','Sup.','SUPERVISEUR_REGIONAL'),
('commission','$2a$10$Zm1ReWsMt2s9NeLd9HaAlOcT0x.k80CPIzDuAGNc8wPkN4nshVsKi','CEL','Res.','RESPONSABLE_CEL'),
('recette','$2a$10$Zm1ReWsMt2s9NeLd9HaAlOcT0x.k80CPIzDuAGNc8wPkN4nshVsKi','Recette','Equipe','ADMIN'),
('alkoutouan','$2a$10$Zm1ReWsMt2s9NeLd9HaAlOcT0x.k80CPIzDuAGNc8wPkN4nshVsKi','Koutouan','Alain-Lucas','ADMIN'),
('miritie','$2a$10$Zm1ReWsMt2s9NeLd9HaAlOcT0x.k80CPIzDuAGNc8wPkN4nshVsKi','Ititié','Maxence','ADMIN');

UPDATE zone
SET login_superviseur = 'region'
WHERE designation = 'ABIDJAN' AND type_zone_id = (SELECT id FROM type_zone WHERE code = 'REGION');

UPDATE commission_locale
SET login_responsable = 'commission'
WHERE code = '010-C033';
