create or replace function charger_election(type varchar, annee integer, prefix varchar, tour2 bool) returns integer as $$

DECLARE
    entrepot_cursor cursor
    for select distinct code_commune, designation_commune from public.entrepot_donnee where synchro = false order by designation_commune;

begin
    FOR entrepot in entrepot_cursor loop
        insert into
        public.election (id, version,
        create_at ,
        create_by ,
        code,
        type,
        annee,
        tour)
        values (nextval('election_id_seq'),
        0,
        current_date,
        'RDD',
        prefix || entrepot.code_commune,
        type,
        annee,
        case tour2 when true then 'SECOND_TOUR'
        when false then 'PREMIER_TOUR' end) on conflict do nothing;

    end loop;
    return 1;
end;
$$ language plpgsql;