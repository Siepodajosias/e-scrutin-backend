create or replace function charger_circonscription_commune() returns integer as $$

DECLARE
entrepot_cursor cursor
for select distinct code_commune, designation_commune from entrepot_donnee where synchro = false  order by designation_commune;

begin
        FOR entrepot in entrepot_cursor loop
                insert into
                circonscription (id, version,
                create_at ,
                create_by ,
                code,
                designation
                )
                values (nextval('circonscription_id_seq'),
                0,
                current_date,
                'RDD',
                entrepot.code_commune,
                entrepot.designation_commune
                ) ON CONFLICT DO NOTHING;
        end loop;

return 1;
end;
$$ language plpgsql;