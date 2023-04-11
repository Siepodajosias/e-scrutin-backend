create or replace function charger_candidat() returns integer as $$
DECLARE
donnee_candidat RECORD;
begin
        FOR donnee_candidat in select * from entrepot_donnee_candidat edc loop
                if donnee_candidat.parti_1 <> ' ' and donnee_candidat.parti_1 is not null then

                        insert into

                        public.candidat (id, version,

                        create_at ,

                        create_by ,

                        nom, ordre,

                        parti_politique_id)

                        values (nextval('candidat_id_seq'),

                        0,

                        current_date,

                        'RDD',

                        donnee_candidat.candidat_1,
1,

                        (select e.id from parti_politique e where trim(e.nom) = trim(donnee_candidat.parti_1))

                        ) on conflict do nothing ;

                        end if;

                        if donnee_candidat.parti_2 <> ' ' and donnee_candidat.parti_2 is not null then

                        insert into

                        public.candidat (id, version,

                        create_at ,

                        create_by ,

                        nom, ordre,

                        parti_politique_id)

                        values (nextval('candidat_id_seq'),

                        0,

                        current_date,

                        'RDD',

                        donnee_candidat.candidat_2,
2,

                        (select e.id from parti_politique e where trim(e.nom) = trim(donnee_candidat.parti_2))

                        ) on conflict do nothing ;

                        end if;
                if donnee_candidat.parti_3 <> ' ' and donnee_candidat.parti_3 is not null then

                        insert into

                        public.candidat (id, version,

                        create_at ,

                        create_by ,

                        nom, ordre,

                        parti_politique_id)

                        values (nextval('candidat_id_seq'),

                        0,

                        current_date,

                        'RDD',

                        donnee_candidat.candidat_3,
3,

                        (select e.id from parti_politique e where trim(e.nom) = trim(donnee_candidat.parti_3))

                        ) on conflict do nothing ;

                        end if;

                if donnee_candidat.parti_4 <> ' ' and donnee_candidat.parti_4 is not null then

                        insert into

                        public.candidat (id, version,

                        create_at ,

                        create_by ,

                        nom, ordre,

                        parti_politique_id)

                        values (nextval('candidat_id_seq'),

                        0,

                        current_date,

                        'RDD',

                        donnee_candidat.candidat_4,
4,

                        (select e.id from parti_politique e where trim(e.nom) = trim(donnee_candidat.parti_4))

                        ) on conflict do nothing ;

                        end if;

                if donnee_candidat.parti_5 <> ' ' and donnee_candidat.parti_5 is not null then

                        insert into

                        public.candidat (id, version,

                        create_at ,

                        create_by ,

                        nom, ordre,

                        parti_politique_id)

                        values (nextval('candidat_id_seq'),

                        0,

                        current_date,

                        'RDD',

                        donnee_candidat.candidat_5,
5,

                        (select e.id from parti_politique e where trim(e.nom) = trim(donnee_candidat.parti_5))

                        ) on conflict do nothing ;

                        end if;

                if donnee_candidat.parti_6 <> ' ' and donnee_candidat.parti_6 is not null then

                        insert into

                        public.candidat (id, version,

                        create_at ,

                        create_by ,

                        nom, ordre,

                        parti_politique_id)

                        values (nextval('candidat_id_seq'),

                        0,

                        current_date,

                        'RDD',

                        donnee_candidat.candidat_6,
6,

                        (select e.id from parti_politique e where trim(e.nom) = trim(donnee_candidat.parti_6))

                        ) on conflict do nothing ;

                        end if;

                if donnee_candidat.parti_7 <> ' ' and donnee_candidat.parti_7 is not null then

                        insert into

                        public.candidat (id, version,

                        create_at ,

                        create_by ,

                        nom, ordre,

                        parti_politique_id)

                        values (nextval('candidat_id_seq'),

                        0,

                        current_date,

                        'RDD',

                        donnee_candidat.candidat_7,
7,

                        (select e.id from parti_politique e where trim(e.nom) = trim(donnee_candidat.parti_7))

                        ) on conflict do nothing ;

                        end if;

                if donnee_candidat.parti_8 <> ' ' and donnee_candidat.parti_8 is not null then

                        insert into

                        public.candidat (id, version,

                        create_at ,

                        create_by ,

                        nom, ordre,

                        parti_politique_id)

                        values (nextval('candidat_id_seq'),

                        0,

                        current_date,

                        'RDD',

                        donnee_candidat.candidat_8,
8,

                        (select e.id from parti_politique e where trim(e.nom) = trim(donnee_candidat.parti_8))

                        ) on conflict do nothing ;

                        end if;

                if donnee_candidat.parti_9 <> ' ' and donnee_candidat.parti_9 is not null then

                        insert into

                        public.candidat (id, version,

                        create_at ,

                        create_by ,

                        nom, ordre,

                        parti_politique_id)

                        values (nextval('candidat_id_seq'),

                        0,

                        current_date,

                        'RDD',

                        donnee_candidat.candidat_9,
9,

                        (select e.id from parti_politique e where trim(e.nom) = trim(donnee_candidat.parti_9))

                        ) on conflict do nothing ;

                        end if;

                if donnee_candidat.parti_10 <> ' ' and donnee_candidat.parti_10 is not null then

                        insert into

                        public.candidat (id, version,

                        create_at ,

                        create_by ,

                        nom, ordre,

                        parti_politique_id)

                        values (nextval('candidat_id_seq'),

                        0,

                        current_date,

                        'RDD',

                        donnee_candidat.candidat_10,
10,

                        (select e.id from parti_politique e where trim(e.nom) = trim(donnee_candidat.parti_10))

                        ) on conflict do nothing ;

                        end if;

                if donnee_candidat.parti_11 <> ' ' and donnee_candidat.parti_11 is not null then

                        insert into

                        public.candidat (id, version,

                        create_at ,

                        create_by ,

                        nom, ordre,

                        parti_politique_id)

                        values (nextval('candidat_id_seq'),

                        0,

                        current_date,

                        'RDD',

                        donnee_candidat.candidat_11,
11,

                        (select e.id from parti_politique e where trim(e.nom) = trim(donnee_candidat.parti_11))

                        ) on conflict do nothing ;

                        end if;

                if donnee_candidat.parti_12 <> ' ' and donnee_candidat.parti_12 is not null then

                        insert into

                        public.candidat (id, version,

                        create_at ,

                        create_by ,

                        nom, ordre,

                        parti_politique_id)

                        values (nextval('candidat_id_seq'),

                        0,

                        current_date,

                        'RDD',

                        donnee_candidat.candidat_12,
12,

                        (select e.id from parti_politique e where trim(e.nom) = trim(donnee_candidat.parti_12))

                        ) on conflict do nothing ;

                        end if;

                if donnee_candidat.parti_13 <> ' ' and donnee_candidat.parti_13 is not null then

                        insert into

                        public.candidat (id, version,

                        create_at ,

                        create_by ,

                        nom, ordre,

                        parti_politique_id)

                        values (nextval('candidat_id_seq'),

                        0,

                        current_date,

                        'RDD',

                        donnee_candidat.candidat_13,
13,

                        (select e.id from parti_politique e where trim(e.nom) = trim(donnee_candidat.parti_13))

                        ) on conflict do nothing ;

                        end if;

                if donnee_candidat.parti_14 <> ' ' and donnee_candidat.parti_14 is not null then

                        insert into

                        public.candidat (id, version,

                        create_at ,

                        create_by ,

                        nom, ordre,

                        parti_politique_id)

                        values (nextval('candidat_id_seq'),

                        0,

                        current_date,

                        'RDD',

                        donnee_candidat.candidat_14,
14,

                        (select e.id from parti_politique e where trim(e.nom) = trim(donnee_candidat.parti_14))

                        ) on conflict do nothing ;

                        end if;

                if donnee_candidat.parti_15 <> ' ' and donnee_candidat.parti_15 is not null then

                        insert into

                        public.candidat (id, version,

                        create_at ,

                        create_by ,

                        nom, ordre,

                        parti_politique_id)

                        values (nextval('candidat_id_seq'),

                        0,

                        current_date,

                        'RDD',

                        donnee_candidat.candidat_15,
15,

                        (select e.id from parti_politique e where trim(e.nom) = trim(donnee_candidat.parti_15))

                        ) on conflict do nothing ;
                        end if;
        end loop;
return 1;
end;
$$ language plpgsql;