create or replace function charger_Synchronisation() returns integer as $$

DECLARE

begin
		update entrepot_donnee set synchro = true  where synchro = false;
		update entrepot_donnee_candidat set synchro = true  where synchro = false;

return 1;
end;
$$ language plpgsql;