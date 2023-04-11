create or replace function charger_bureau_vote_comune() returns integer as $$

DECLARE
entrepot_cursor cursor

for select distinct code_commission_locale, designation_commission_locale, code_lieu_vote , code_bureau_vote , designation_lieu_vote, designation_commune, z.id as id_zone  from entrepot_donnee join "zone" z on trim(z.designation) = trim(replace(designation_commune, 'COMMUNE', '')) where z.type_zone_id = 21 order by designation_lieu_vote ;
begin
        FOR entrepot in entrepot_cursor loop
                insert into
                bureau_vote (id, version,
                create_at ,
                create_by ,
                code,
                designation,
                lieu_vote_id,
                zone_id)
                values (nextval('bureau_vote_id_seq'),
                0,
                current_date,
                'RDD',
                entrepot.code_lieu_vote || entrepot.code_bureau_vote,
                entrepot.designation_lieu_vote,
                (select l.id from lieu_vote l join commission_locale c on c.id = l.commission_locale_id
                where l.code = entrepot.code_lieu_vote and c.code = entrepot.code_commission_locale and trim(c.designation) = trim(entrepot.designation_commission_locale)),
                entrepot.id_zone)
                ON CONFLICT DO NOTHING;
        end loop;
        return 1;
end;

$$ language plpgsql;

