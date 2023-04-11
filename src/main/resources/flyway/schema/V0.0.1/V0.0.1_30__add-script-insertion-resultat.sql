create or replace function charger_resultat(typeElection varchar, anneeElection integer, tourElection varchar) returns integer as $$

DECLARE
	entrepot_cursor cursor
	for select distinct nombre_inscrits, total_votants, nombre_bulletin_nuls, nombre_bulletin_blancs, taux_participation, homme_votants, femme_votantes, suffrage_exprimes, code_lieu_vote, code_bureau_vote from entrepot_donnee where synchro = false order by code_lieu_vote ;
begin
        FOR entrepot in entrepot_cursor loop
                insert into
                resultat (id, version,
                create_at ,
                create_by ,
                nombre_votants,
                nombre_inscrits ,
                nombre_hommes_votants ,
                nombre_femmes_votantes,
                nombre_bulletins_nuls,
                nombre_bulletins_blancs,
                taux_participation,
                suffrages_exprimes,
                bureau_vote_id,
                election_id)
                values (nextval('resultat_id_seq'),
                0,
                current_date,
                'RDD',
                entrepot.total_votants,
                entrepot.nombre_inscrits,
                entrepot.homme_votants,
                entrepot.femme_votantes,
                entrepot.nombre_bulletin_nuls,
                entrepot.nombre_bulletin_blancs,
                entrepot.taux_participation,
                entrepot.suffrage_exprimes,
                (select bv.id from bureau_vote bv where bv.code = entrepot.code_lieu_vote || entrepot.code_bureau_vote),
                (select e.id from election e where e."type" = typeElection and e.annee = anneeElection and e.tour = tourElection));
        end loop;
        return 1;
end;

$$ language plpgsql;