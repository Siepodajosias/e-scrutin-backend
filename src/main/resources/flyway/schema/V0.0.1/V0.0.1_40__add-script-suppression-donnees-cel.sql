create or replace function supprimer_donnees_commission_locale(nom_commission varchar, nom_circonscription varchar, anneeElection integer, typeElection varchar) returns integer as $$

DECLARE
entrepot_don RECORD;
entrepot_can RECORD;
begin

	delete from join_resultat_candidat_election jrce where jrce.id in (select jrce2.id from join_resultat_candidat_election jrce2
		join resultat r on r.id = jrce2.resultat_id
		join bureau_vote bv on bv.id = r.bureau_vote_id
		join lieu_vote lv on lv.id = bv.lieu_vote_id
		join commission_locale cl on cl.id = lv.commission_locale_id
		join circonscription c on c.id = cl.circonscription_id
		join election e on r.election_id = e.id
		where trim(cl.designation) = trim(nom_commission) and trim(c.designation) = trim(nom_circonscription) and e.annee = anneeElection and e.type = typeElection);

	delete from candidat c1  where c1.id in (select c2.id from candidat c2
		left join join_resultat_candidat_election jrce on c2.id = jrce.candidat_id
		where jrce is null);

	delete from parti_politique p1  where p1.id in (select p2.id from parti_politique p2
		left join candidat c on c.parti_politique_id = p2.id
		where c is null);


	delete from resultat r2 where r2.id in (select r3.id from resultat r3
		join bureau_vote bv on bv.id = r3.bureau_vote_id
		join lieu_vote lv on lv.id = bv.lieu_vote_id
		join commission_locale cl on cl.id = lv.commission_locale_id
		join circonscription c on c.id = cl.circonscription_id
		left join join_resultat_candidat_election jrce on jrce.resultat_id = r3.id
		where trim(cl.designation) = trim(nom_commission) and trim(c.designation) = trim(nom_circonscription) and jrce is null);

	delete from bureau_vote bv where bv.id in (select bv2.id from bureau_vote bv2
		join lieu_vote lv on lv.id = bv2.lieu_vote_id
		join commission_locale cl on cl.id = lv.commission_locale_id
		join circonscription c on c.id = cl.circonscription_id
		left join resultat r4 on r4.bureau_vote_id = bv2.id
		where trim(cl.designation) = trim(nom_commission) and trim(c.designation) = trim(nom_circonscription) and r4 is null);

	delete from lieu_vote lv where lv.id in (select lv2.id from lieu_vote lv2
		join commission_locale cl on cl.id = lv2.commission_locale_id
		join circonscription c on c.id = cl.circonscription_id
		left join bureau_vote bv3 on bv3.lieu_vote_id = lv2.id
		where trim(cl.designation) = trim(nom_commission) and trim(c.designation) = trim(nom_circonscription) and bv3 is null);

	delete from commission_locale cl where cl.id in (select cl2.id from commission_locale cl2
		join circonscription c on c.id = cl2.circonscription_id
		left join lieu_vote lv3 on lv3.commission_locale_id = cl2.id
		where trim(cl.designation) = trim(nom_commission) and trim(c.designation) = trim(nom_circonscription) and lv3 is null);

	for entrepot_don in select * from  entrepot_donnee e where trim(e.designation_commission_locale) = trim(nom_commission)
	and trim(e.designation_commune) = trim(nom_circonscription) and e.annee = anneeElection and e.type_election = typeElection loop
	    INSERT INTO public.entrepot_donnee_archives (code_commune,designation_commune,code_commission_locale,designation_commission_locale,code_lieu_vote,designation_lieu_vote,code_bureau_vote,nombre_inscrits,homme_votants,femme_votantes,total_votants,taux_participation,nombre_bulletin_nuls,nombre_bulletin_blancs,suffrage_exprimes,score_1,score_2,score_3,score_4,score_5,score_6,score_7,score_8,score_9,score_10,score_11,score_12,score_13,score_14,score_15,synchro,create_by,create_at,nom_fcihier,annee,type_election) VALUES
        	 (entrepot_don.code_commune,entrepot_don.designation_commune,entrepot_don.code_commission_locale,entrepot_don.designation_commission_locale,entrepot_don.code_lieu_vote,entrepot_don.designation_lieu_vote,entrepot_don.code_bureau_vote,entrepot_don.nombre_inscrits,entrepot_don.homme_votants,entrepot_don.femme_votantes,entrepot_don.total_votants,entrepot_don.taux_participation,entrepot_don.nombre_bulletin_nuls,entrepot_don.nombre_bulletin_blancs,entrepot_don.suffrage_exprimes,entrepot_don.score_1,
        	 entrepot_don.score_2,entrepot_don.score_3,entrepot_don.score_4,entrepot_don.score_5,entrepot_don.score_6,entrepot_don.score_7,entrepot_don.score_8,entrepot_don.score_9,entrepot_don.score_10,entrepot_don.score_11,entrepot_don.score_12,entrepot_don.score_13,entrepot_don.score_14,entrepot_don.score_15,entrepot_don.synchro,entrepot_don.create_by,entrepot_don.create_at,entrepot_don.nom_fcihier,entrepot_don.annee,entrepot_don.type_election);
        delete from entrepot_donnee where id = entrepot_don.id;
	end loop;
    for entrepot_can in select * from entrepot_donnee_candidat e where trim(e.designation_commission_locale) = trim(nom_commission)
    	and trim(e.designation_commune) = trim(nom_circonscription) and e.annee = anneeElection and e.type_election = typeElection loop
    	    INSERT INTO public.entrepot_donnee_candidat_archives (code_commune,designation_commune,code_commission_locale,designation_commission_locale,parti_1,candidat_1,parti_2,candidat_2,parti_3,candidat_3,parti_4,candidat_4,parti_5,candidat_5,parti_6,candidat_6,parti_7,candidat_7,parti_8,candidat_8,parti_9,candidat_9,parti_10,candidat_10,parti_11,candidat_11,parti_12,candidat_12,parti_13,candidat_13,parti_14,candidat_14,parti_15,candidat_15,synchro,create_by,create_at,nom_fcihier,annee,type_election) VALUES
            	 (entrepot_can.code_commune,entrepot_can.designation_commune,entrepot_can.code_commission_locale,entrepot_can.designation_commission_locale,entrepot_can.parti_1,entrepot_can.candidat_1,entrepot_can.parti_2,entrepot_can.candidat_2,entrepot_can.parti_3,entrepot_can.candidat_3,entrepot_can.parti_4,entrepot_can.candidat_4,entrepot_can.parti_5,entrepot_can.candidat_5,entrepot_can.parti_6,entrepot_can.candidat_6,entrepot_can.parti_7,entrepot_can.candidat_7,entrepot_can.parti_8,
            	 entrepot_can.candidat_8,entrepot_can.parti_9,entrepot_can.candidat_9,entrepot_can.parti_10,entrepot_can.candidat_10,entrepot_can.parti_11,entrepot_can.candidat_11,entrepot_can.parti_12,entrepot_can.candidat_12,entrepot_can.parti_13,entrepot_can.candidat_13,entrepot_can.parti_14,entrepot_can.candidat_14,entrepot_can.parti_15,entrepot_can.candidat_15,entrepot_can.synchro,entrepot_can.create_by,entrepot_can.create_at,entrepot_can.nom_fcihier,entrepot_can.annee,entrepot_can.type_election);

            delete from entrepot_donnee_candidat where id = entrepot_can.id;
    	end loop;
    return 1;
end;
$$ language plpgsql;