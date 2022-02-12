CREATE or REPLACE function validatePersonKey()
	RETURNS trigger
	LANGUAGE plpgsql
AS $$
declare exist integer;

/* NEW = carrega os dados que estao sendo inseridos (INSERT) e atualizados (UPDATE) */
/* OLD = carrega os dados da linha antiga antes de atualizar (UPDATE) */
BEGIN
	exist = (select count(1) from natural_person where id = NEW.person_id);
	if(exist <=0) then
		exist = (select count(1) from legal_person where id = NEW.person_id);
		if(exist <= 0) then
			RAISE EXCEPTION 'ID or PK not found to do an asssociation [RLSP]';
        end if;
    end if;
    return NEW;
END;
$$

/* Product Evaluation */
CREATE TRIGGER validatePersonKey_ProductEvaluation_Update
    BEFORE UPDATE
    ON product_evaluation
    FOR EACH ROW
    EXECUTE PROCEDURE validatePersonKey();

CREATE TRIGGER validatePersonKey_ProductEvaluation_Insert
    BEFORE INSERT
    ON product_evaluation
    FOR EACH ROW
    EXECUTE PROCEDURE validatePersonKey();

/* Trade Payable */

CREATE TRIGGER validatePersonKey_TradePayable_Person_Update
    BEFORE UPDATE
    ON trade_payable
    FOR EACH ROW
    EXECUTE PROCEDURE validatePersonKey();

CREATE TRIGGER validatePersonKey_TradePayable_Person_Insert
    BEFORE INSERT
    ON trade_payable
    FOR EACH ROW
    EXECUTE PROCEDURE validatePersonKey();

/* Trade Receivable */

CREATE TRIGGER validatePersonKey_TradeReceivable_Person_Update
    BEFORE UPDATE
    ON trade_receivable
    FOR EACH ROW
    EXECUTE PROCEDURE validatePersonKey();

CREATE TRIGGER validatePersonKey_TradeReceivable_Person_Insert
    BEFORE INSERT
    ON trade_receivable
    FOR EACH ROW
    EXECUTE PROCEDURE validatePersonKey();