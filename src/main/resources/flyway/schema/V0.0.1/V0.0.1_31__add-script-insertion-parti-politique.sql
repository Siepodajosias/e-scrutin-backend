create or replace function charger_parti() returns integer as $$
DECLARE
donnee_candidat RECORD;
begin

        FOR donnee_candidat in select * from entrepot_donnee_candidat edc where edc.synchro = false loop

                if donnee_candidat.parti_1 <> ' ' and donnee_candidat.parti_1 is not null then

                        insert into

                        public.parti_politique (id, version,

                        create_at ,

                        create_by ,

                        nom)

                        values (nextval('parti_politique_id_seq'),
                        0,

                        current_date,

                        'RDD',

                        donnee_candidat.parti_1

                        ) on conflict do nothing ;

                        end if;
                if donnee_candidat.parti_2 <> ' ' and donnee_candidat.parti_2 is not null then

                        insert into

                        public.parti_politique (id, version,

                        create_at ,

                        create_by ,

                        nom)

                        values (nextval('parti_politique_id_seq'),
                        0,

                        current_date,

                        'RDD',

                        donnee_candidat.parti_2

                        ) on conflict do nothing ;

                        end if;

               if donnee_candidat.parti_3 <> ' ' and donnee_candidat.parti_3 is not null then

                        insert into

                        public.parti_politique (id, version,

                        create_at ,

                        create_by ,

                        nom)

                        values (nextval('parti_politique_id_seq'),
                        0,

                        current_date,

                        'RDD',

                        donnee_candidat.parti_3

                        ) on conflict do nothing ;

                        end if;

               if donnee_candidat.parti_4 <> ' ' and donnee_candidat.parti_4 is not null then

                        insert into

                        public.parti_politique (id, version,

                        create_at ,

                        create_by ,

                        nom)

                        values (nextval('parti_politique_id_seq'),
                        0,

                        current_date,

                        'RDD',

                        donnee_candidat.parti_4

                        ) on conflict do nothing ;

                        end if;

                 if donnee_candidat.parti_5 <> ' ' and donnee_candidat.parti_5 is not null then

                        insert into

                        public.parti_politique (id, version,

                        create_at ,

                        create_by ,

                        nom)

                        values (nextval('parti_politique_id_seq'),
                        0,

                        current_date,

                        'RDD',

                        donnee_candidat.parti_5

                        ) on conflict do nothing ;

                        end if;

                  if donnee_candidat.parti_6 <> ' ' and donnee_candidat.parti_6 is not null then

                        insert into

                        public.parti_politique (id, version,

                        create_at ,

                        create_by ,

                        nom)

                        values (nextval('parti_politique_id_seq'),
                        0,

                        current_date,

                        'RDD',

                        donnee_candidat.parti_6

                        ) on conflict do nothing ;

                        end if;


                       if donnee_candidat.parti_7 <> ' ' and donnee_candidat.parti_7 is not null then

                        insert into

                        public.parti_politique (id, version,

                        create_at ,

                        create_by ,

                        nom)

                        values (nextval('parti_politique_id_seq'),
                        0,

                        current_date,

                        'RDD',

                        donnee_candidat.parti_7

                        ) on conflict do nothing ;

                        end if;

                       if donnee_candidat.parti_8 <> ' ' and donnee_candidat.parti_8 is not null then

                        insert into

                        public.parti_politique (id, version,

                        create_at ,

                        create_by ,

                        nom)

                        values (nextval('parti_politique_id_seq'),
                        0,

                        current_date,

                        'RDD',

                        donnee_candidat.parti_8

                        ) on conflict do nothing ;

                        end if;

                 if donnee_candidat.parti_9 <> ' ' and donnee_candidat.parti_9 is not null then

                        insert into

                        public.parti_politique (id, version,

                        create_at ,

                        create_by ,

                        nom)

                        values (nextval('parti_politique_id_seq'),
                        0,

                        current_date,

                        'RDD',

                        donnee_candidat.parti_9

                        ) on conflict do nothing ;

                        end if;

                       if donnee_candidat.parti_10 <> ' ' and donnee_candidat.parti_10 is not null then

                        insert into

                        public.parti_politique (id, version,

                        create_at ,

                        create_by ,

                        nom)

                        values (nextval('parti_politique_id_seq'),
                        0,

                        current_date,

                        'RDD',

                        donnee_candidat.parti_10

                        ) on conflict do nothing ;

                        end if;

                  if donnee_candidat.parti_11 <> ' ' and donnee_candidat.parti_11 is not null then

                        insert into

                        public.parti_politique (id, version,

                        create_at ,

                        create_by ,

                        nom)

                        values (nextval('parti_politique_id_seq'),
                        0,

                        current_date,

                        'RDD',

                        donnee_candidat.parti_11

                        ) on conflict do nothing ;

                        end if;

                   if donnee_candidat.parti_12 <> ' ' and donnee_candidat.parti_12 is not null then

                        insert into

                        public.parti_politique (id, version,

                        create_at ,

                        create_by ,

                        nom)

                        values (nextval('parti_politique_id_seq'),
                        0,

                        current_date,

                        'RDD',

                        donnee_candidat.parti_12

                        ) on conflict do nothing ;

                        end if;

                       if donnee_candidat.parti_13 <> ' ' and donnee_candidat.parti_13 is not null then

                        insert into

                        public.parti_politique (id, version,

                        create_at ,

                        create_by ,

                        nom)

                        values (nextval('parti_politique_id_seq'),
                        0,

                        current_date,

                        'RDD',

                        donnee_candidat.parti_13

                        ) on conflict do nothing ;

                        end if;


                       if donnee_candidat.parti_14 <> ' ' and donnee_candidat.parti_14 is not null then

                        insert into

                        public.parti_politique (id, version,

                        create_at ,

                        create_by ,

                        nom)

                        values (nextval('parti_politique_id_seq'),
                        0,

                        current_date,

                        'RDD',

                        donnee_candidat.parti_14

                        ) on conflict do nothing ;

                        end if;

                       if donnee_candidat.parti_15 <> ' ' and donnee_candidat.parti_15 is not null then

                        insert into

                        public.parti_politique (id, version,

                        create_at ,

                        create_by ,

                        nom)

                        values (nextval('parti_politique_id_seq'),
                        0,

                        current_date,

                        'RDD',

                        donnee_candidat.parti_15

                        ) on conflict do nothing ;

                        end if;
        end loop;
return 1;
end;
$$ language plpgsql;

