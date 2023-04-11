create or replace function charger_lieu_vote() returns integer as $$

DECLARE
    entrepot_cursor cursor
    for select distinct code_commune, designation_commune, designation_commission_locale, code_commission_locale, code_lieu_vote , designation_lieu_vote from entrepot_donnee where synchro = false order by designation_lieu_vote ;

begin
        FOR entrepot in entrepot_cursor loop
        insert into
        lieu_vote (id, version,
        create_at ,
        create_by ,
        code,
        designation,
        commission_locale_id)
        values (nextval('lieu_vote_id_seq'),
        0,
        current_date,
        'RDD',
        entrepot.code_lieu_vote,
        entrepot.designation_lieu_vote,
        (select cl.id from commission_locale cl join circonscription c on cl.circonscription_id = c.id
         where cl.code = entrepot.code_commission_locale and trim(cl.designation) = trim(entrepot.designation_commission_locale)
         and c.code = entrepot.code_commune and trim(c.designation) = trim(entrepot.designation_commune)))
         ON CONFLICT DO NOTHING;
        end loop;
        return 1;
end;
$$ language plpgsql;

