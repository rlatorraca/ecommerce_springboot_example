CREATE or REPLACE function validatePersonProviderKey()
	RETURNS trigger
	LANGUAGE plpgsql
AS $$
declare exist integer;

/* NEW = carrega os dados que estao sendo inseridos (INSERT) e atualizados (UPDATE) */
/* OLD = carrega os dados da linha antiga antes de atualizar (UPDATE) */
BEGIN
	exist = (select count(1) from natural_person where id = NEW.person_provider_id);
	if(exist <=0) then
		exist = (select count(1) from legal_person where id = NEW.person_provider_id);
		if(exist <= 0) then
			RAISE EXCEPTION 'ID or PK not found to do an asssociation [RLSP]';
        end if;
    end if;
    return NEW;
END;
$$


CREATE TRIGGER validatePersonKey_TradePayable_PersonProvider_Update
    BEFORE UPDATE
    ON trade_payable
    FOR EACH ROW
    EXECUTE PROCEDURE validatePersonProviderKey();

CREATE TRIGGER validatePersonKey_TradePayable_PersonProvider_Insert
    BEFORE INSERT
    ON trade_payable
    FOR EACH ROW
    EXECUTE PROCEDURE validatePersonProviderKey();