create or replace function charger_commission_locale() returns integer as $$
DECLARE
    entrepot_cursor cursor
    for select distinct designation_commune, code_commune, code_commission_locale , designation_commission_locale from entrepot_donnee where synchro = false order by designation_commission_locale;
begin
        FOR entrepot in entrepot_cursor loop
                insert into
                commission_locale (id, version,
                create_at ,
                create_by ,
                code,
                designation,
                circonscription_id)
                values (nextval('commission_locale_id_seq'),
                0,
                current_date,
                'RDD',
                entrepot.code_commission_locale,
                entrepot.designation_commission_locale,
                (select id from circonscription c where c.code = entrepot.code_commune and trim(c.designation) = trim(entrepot.designation_commune)))
                ON CONFLICT DO NOTHING;
        end loop;
        return 1;
end;

$$ language plpgsql;
