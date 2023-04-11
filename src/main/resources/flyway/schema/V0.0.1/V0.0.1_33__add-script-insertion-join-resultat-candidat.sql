create or replace function charger_join_resultat_candidat_election(typeElection varchar, anneeElection integer, tourElection varchar) returns integer as $$

DECLARE

donnee_candidat RECORD;
donnee_entrepot RECORD;
donnee_resultat RECORD;

begin
		FOR  donnee_candidat  in select * from entrepot_donnee_candidat edc where edc.synchro = false loop
			FOR donnee_entrepot in select * from entrepot_donnee ed  where ed.synchro = false and trim(ed.code_commission_locale) = trim(donnee_candidat.code_commission_locale) and trim(ed.designation_commission_locale) = trim(donnee_candidat.designation_commission_locale) loop
	        		for donnee_resultat in select * from resultat r join bureau_vote bv on r.bureau_vote_id = bv.id join election e on e.id  = r.election_id  where bv.code =  donnee_entrepot.code_lieu_vote || donnee_entrepot.code_bureau_vote and e.annee  = anneeElection and
	        		 e."type" = typeElection and e.tour = tourElection loop


				if donnee_candidat.candidat_1 <> ' ' and donnee_candidat.candidat_1 is not null then

	                        insert into
	                        public.join_resultat_candidat_election (id, version,
	                        create_at ,
	                        create_by ,
	                        candidat_id,
	                        resultat_id,
	                        voix)
	                        values (nextval('join_resultat_candidat_election_id_seq'),
	                        0,
	                        current_date,
	                        'RDD',
	                        (select c.id from candidat c join parti_politique pp on c.parti_politique_id = pp.id  where c.nom = donnee_candidat.candidat_1 and pp.nom = donnee_candidat.parti_1 ),
	                        donnee_resultat.id,
	                        donnee_entrepot.score_1
	                        ) on conflict do nothing;

	               end if;

	              if donnee_candidat.candidat_2 <> ' ' and donnee_candidat.candidat_2 is not null then

	                        insert into
	                        public.join_resultat_candidat_election (id, version,
	                        create_at ,
	                        create_by ,
	                        candidat_id,
	                        resultat_id,
	                        voix)
	                        values (nextval('join_resultat_candidat_election_id_seq'),
	                        0,
	                        current_date,
	                        'RDD',
	                        (select c.id from candidat c join parti_politique pp on c.parti_politique_id = pp.id  where c.nom = donnee_candidat.candidat_2 and pp.nom = donnee_candidat.parti_2 ),
	                        donnee_resultat.id,
	                        donnee_entrepot.score_2
	                        ) on conflict do nothing;

	               end if;

	              if donnee_candidat.candidat_3 <> ' ' and donnee_candidat.candidat_3 is not null then

	                        insert into
	                        public.join_resultat_candidat_election (id, version,
	                        create_at ,
	                        create_by ,
	                        candidat_id,
	                        resultat_id,
	                        voix)
	                        values (nextval('join_resultat_candidat_election_id_seq'),
	                        0,
	                        current_date,
	                        'RDD',
	                        (select c.id from candidat c join parti_politique pp on c.parti_politique_id = pp.id  where c.nom = donnee_candidat.candidat_3 and pp.nom = donnee_candidat.parti_3 ),
	                        donnee_resultat.id,
	                        donnee_entrepot.score_3
	                        ) on conflict do nothing;

	               end if;
	              if donnee_candidat.candidat_4 <> ' ' and donnee_candidat.candidat_4 is not null then

	                        insert into
	                        public.join_resultat_candidat_election (id, version,
	                        create_at ,
	                        create_by ,
	                        candidat_id,
	                        resultat_id,
	                        voix)
	                        values (nextval('join_resultat_candidat_election_id_seq'),
	                        0,
	                        current_date,
	                        'RDD',
	                        (select c.id from candidat c join parti_politique pp on c.parti_politique_id = pp.id  where c.nom = donnee_candidat.candidat_4 and pp.nom = donnee_candidat.parti_4 ),
	                        donnee_resultat.id,
	                        donnee_entrepot.score_4
	                        ) on conflict do nothing;

	               end if;
	              if donnee_candidat.candidat_5 <> ' ' and donnee_candidat.candidat_5 is not null then

	                        insert into
	                        public.join_resultat_candidat_election (id, version,
	                        create_at ,
	                        create_by ,
	                        candidat_id,
	                        resultat_id,
	                        voix)
	                        values (nextval('join_resultat_candidat_election_id_seq'),
	                        0,
	                        current_date,
	                        'RDD',
	                        (select c.id from candidat c join parti_politique pp on c.parti_politique_id = pp.id  where c.nom = donnee_candidat.candidat_5 and pp.nom = donnee_candidat.parti_5 ),
	                        donnee_resultat.id,
	                        donnee_entrepot.score_5
	                        ) on conflict do nothing;

	               end if;
	              if donnee_candidat.candidat_6 <> ' ' and donnee_candidat.candidat_6 is not null then

	                        insert into
	                        public.join_resultat_candidat_election (id, version,
	                        create_at ,
	                        create_by ,
	                        candidat_id,
	                        resultat_id,
	                        voix)
	                        values (nextval('join_resultat_candidat_election_id_seq'),
	                        0,
	                        current_date,
	                        'RDD',
	                        (select c.id from candidat c join parti_politique pp on c.parti_politique_id = pp.id  where c.nom = donnee_candidat.candidat_6 and pp.nom = donnee_candidat.parti_6 ),
	                        donnee_resultat.id,
	                        donnee_entrepot.score_6
	                        ) on conflict do nothing;

	               end if;
	              if donnee_candidat.candidat_7 <> ' ' and donnee_candidat.candidat_7 is not null then

	                        insert into
	                        public.join_resultat_candidat_election (id, version,
	                        create_at ,
	                        create_by ,
	                        candidat_id,
	                        resultat_id,
	                        voix)
	                        values (nextval('join_resultat_candidat_election_id_seq'),
	                        0,
	                        current_date,
	                        'RDD',
	                        (select c.id from candidat c join parti_politique pp on c.parti_politique_id = pp.id  where c.nom = donnee_candidat.candidat_7 and pp.nom = donnee_candidat.parti_7 ),
	                        donnee_resultat.id,
	                        donnee_entrepot.score_7
	                        ) on conflict do nothing;

	               end if;
	              if donnee_candidat.candidat_8 <> ' ' and donnee_candidat.candidat_8 is not null then

	                        insert into
	                        public.join_resultat_candidat_election (id, version,
	                        create_at ,
	                        create_by ,
	                        candidat_id,
	                        resultat_id,
	                        voix)
	                        values (nextval('join_resultat_candidat_election_id_seq'),
	                        0,
	                        current_date,
	                        'RDD',
	                        (select c.id from candidat c join parti_politique pp on c.parti_politique_id = pp.id  where c.nom = donnee_candidat.candidat_8 and pp.nom = donnee_candidat.parti_8 ),
	                        donnee_resultat.id,
	                        donnee_entrepot.score_8
	                        ) on conflict do nothing;

	               end if;
	              if donnee_candidat.candidat_9 <> ' ' and donnee_candidat.candidat_9 is not null then

	                        insert into
	                        public.join_resultat_candidat_election (id, version,
	                        create_at ,
	                        create_by ,
	                        candidat_id,
	                        resultat_id,
	                        voix)
	                        values (nextval('join_resultat_candidat_election_id_seq'),
	                        0,
	                        current_date,
	                        'RDD',
	                        (select c.id from candidat c join parti_politique pp on c.parti_politique_id = pp.id  where c.nom = donnee_candidat.candidat_9 and pp.nom = donnee_candidat.parti_9 ),
	                        donnee_resultat.id,
	                        donnee_entrepot.score_9
	                        ) on conflict do nothing;

	               end if;
	              if donnee_candidat.candidat_10 <> ' ' and donnee_candidat.candidat_10 is not null then

	                        insert into
	                        public.join_resultat_candidat_election (id, version,
	                        create_at ,
	                        create_by ,
	                        candidat_id,
	                        resultat_id,
	                        voix)
	                        values (nextval('join_resultat_candidat_election_id_seq'),
	                        0,
	                        current_date,
	                        'RDD',
	                        (select c.id from candidat c join parti_politique pp on c.parti_politique_id = pp.id  where c.nom = donnee_candidat.candidat_10 and pp.nom = donnee_candidat.parti_10 ),
	                        donnee_resultat.id,
	                        donnee_entrepot.score_10
	                        ) on conflict do nothing;

	               end if;
	              if donnee_candidat.candidat_11 <> ' ' and donnee_candidat.candidat_11 is not null then

	                        insert into
	                        public.join_resultat_candidat_election (id, version,
	                        create_at ,
	                        create_by ,
	                        candidat_id,
	                        resultat_id,
	                        voix)
	                        values (nextval('join_resultat_candidat_election_id_seq'),
	                        0,
	                        current_date,
	                        'RDD',
	                        (select c.id from candidat c join parti_politique pp on c.parti_politique_id = pp.id  where c.nom = donnee_candidat.candidat_11 and pp.nom = donnee_candidat.parti_11 ),
	                        donnee_resultat.id,
	                        donnee_entrepot.score_11
	                        ) on conflict do nothing;

	               end if;
	              if donnee_candidat.candidat_12 <> ' ' and donnee_candidat.candidat_12 is not null then

	                        insert into
	                        public.join_resultat_candidat_election (id, version,
	                        create_at ,
	                        create_by ,
	                        candidat_id,
	                        resultat_id,
	                        voix)
	                        values (nextval('join_resultat_candidat_election_id_seq'),
	                        0,
	                        current_date,
	                        'RDD',
	                        (select c.id from candidat c join parti_politique pp on c.parti_politique_id = pp.id  where c.nom = donnee_candidat.candidat_12 and pp.nom = donnee_candidat.parti_12 ),
	                        donnee_resultat.id,
	                        donnee_entrepot.score_12
	                        ) on conflict do nothing;

	               end if;
	              if donnee_candidat.candidat_13 <> ' ' and donnee_candidat.candidat_13 is not null then

	                        insert into
	                        public.join_resultat_candidat_election (id, version,
	                        create_at ,
	                        create_by ,
	                        candidat_id,
	                        resultat_id,
	                        voix)
	                        values (nextval('join_resultat_candidat_election_id_seq'),
	                        0,
	                        current_date,
	                        'RDD',
	                        (select c.id from candidat c join parti_politique pp on c.parti_politique_id = pp.id  where c.nom = donnee_candidat.candidat_13 and pp.nom = donnee_candidat.parti_13 ),
	                        donnee_resultat.id,
	                        donnee_entrepot.score_13
	                        ) on conflict do nothing;

	               end if;
	              if donnee_candidat.candidat_14 <> ' ' and donnee_candidat.candidat_14 is not null then

	                        insert into
	                        public.join_resultat_candidat_election (id, version,
	                        create_at ,
	                        create_by ,
	                        candidat_id,
	                        resultat_id,
	                        voix)
	                        values (nextval('join_resultat_candidat_election_id_seq'),
	                        0,
	                        current_date,
	                        'RDD',
	                        (select c.id from candidat c join parti_politique pp on c.parti_politique_id = pp.id  where c.nom = donnee_candidat.candidat_14 and pp.nom = donnee_candidat.parti_14 ),
	                        donnee_resultat.id,
	                        donnee_entrepot.score_14
	                        ) on conflict do nothing;

	               end if;
	              if donnee_candidat.candidat_15 <> ' ' and donnee_candidat.candidat_15 is not null then

	                        insert into
	                        public.join_resultat_candidat_election (id, version,
	                        create_at ,
	                        create_by ,
	                        candidat_id,
	                        resultat_id,
	                        voix)
	                        values (nextval('join_resultat_candidat_election_id_seq'),
	                        0,
	                        current_date,
	                        'RDD',
	                        (select c.id from candidat c join parti_politique pp on c.parti_politique_id = pp.id  where c.nom = donnee_candidat.candidat_15 and pp.nom = donnee_candidat.parti_15 ),
	                        donnee_resultat.id,
	                        donnee_entrepot.score_15
	                        ) on conflict do nothing;

	               end if;
	          end loop;
	     end loop;

        end loop;
return 1;
end;
$$ language plpgsql;

