package ca.com.rlsp.ecommerce.controller;

import ca.com.rlsp.ecommerce.exception.EcommerceException;
import ca.com.rlsp.ecommerce.model.TradePayable;
import ca.com.rlsp.ecommerce.service.TradePayableService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
public class TradePayableController {

    private static final String ERROR_GETTING_TRADE_PAYABLE_BY_ID = "Error getting Trade Payable by id/code [RLSP] : ";
    private static final String ERROR_TRADE_PAYABLE_EXIST_ON_DB = "Trade payable having that description already exist on DB [RLSP] : ";
    private static final String ERROR_ECOMMERCE_COMPANY_MUST_BE_INFORMED= "Ecommerce Company must be informed [RLSP] : ";
    private static final String ERROR_NATURAL_PERSON_MUST_BE_INFORMED= "Natural Person responsible must be informed [RLSP] : ";

    private final TradePayableService tradePayableService;

    public TradePayableController(TradePayableService tradePayableService) {
        this.tradePayableService = tradePayableService;
    }

    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @GetMapping(value = "/getAllTradePayable")
    public ResponseEntity<Collection<TradePayable>> getAllTradePayable(){

        Collection<TradePayable> allTradePayable = tradePayableService.getAll();
        return new ResponseEntity<>(allTradePayable, HttpStatus.OK);

    }

    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @GetMapping(value = "/getTradePayable/{tradePayableId}")
    public ResponseEntity<TradePayable> getTradePayableById(@PathVariable Long tradePayableId) throws EcommerceException {

        TradePayable tradePayableSaved = tradePayableService.getTradePayableById(tradePayableId).orElse(null);

        if(tradePayableSaved == null) {
            throw new EcommerceException(ERROR_GETTING_TRADE_PAYABLE_BY_ID + tradePayableId);
        }
        return new ResponseEntity<>(tradePayableSaved, HttpStatus.OK);
    }

    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @PostMapping(path = "/saveTradePayable")
    public ResponseEntity<TradePayable> savingTradePayable(@RequestBody @Valid TradePayable tradePayable) throws EcommerceException {

        if (tradePayable.getId() == null) {
            List<TradePayable> tradePayableList = tradePayableService.getByDescription(tradePayable.getDescription().toUpperCase().trim());

            if (!tradePayableList.isEmpty()) {
                throw new EcommerceException(ERROR_TRADE_PAYABLE_EXIST_ON_DB + tradePayable.getDescription());
            }

            if (tradePayable.getPerson() == null || tradePayable.getPerson().getId() <= 0) {
                throw new EcommerceException(ERROR_NATURAL_PERSON_MUST_BE_INFORMED);
            }

            if (tradePayable.getEcommerceCompany() == null || tradePayable.getEcommerceCompany().getId() <= 0) {
                throw new EcommerceException(ERROR_ECOMMERCE_COMPANY_MUST_BE_INFORMED);
            }
        }


        TradePayable tradePayableSaved = tradePayableService.save(tradePayable);

        return new ResponseEntity<TradePayable>(tradePayableSaved, HttpStatus.OK);


    }


    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @DeleteMapping(path = "/deleteTradePayable")
    public ResponseEntity<?> deleteTradePayable(@RequestBody TradePayable tradePayable){

        tradePayableService.deleteById(tradePayable.getId());
        return new ResponseEntity<>("Deleted Trade Payable By Object", HttpStatus.OK);
    }

    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @DeleteMapping(value = "/deleteTradePayable/{tradePayableId}")
    public ResponseEntity<?> deleteTradepayableById(@PathVariable Long tradePayableId){

        tradePayableService.deleteById(tradePayableId);

        return new ResponseEntity<>("Deleted Trade Payable By Id: " + tradePayableId, HttpStatus.OK);
    }

    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @GetMapping(value = "/getTradePayable/description/{description}")
    public ResponseEntity<List<TradePayable>> getByDescriptionTradePayable(@PathVariable String description){

        List<TradePayable> tradePayableList = tradePayableService.getByDescription(description.toUpperCase());

        return new ResponseEntity<>(tradePayableList, HttpStatus.OK);
    }
}
