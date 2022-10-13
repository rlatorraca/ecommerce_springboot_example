package ca.com.rlsp.ecommerce.controller;

import ca.com.rlsp.ecommerce.exception.EcommerceException;
import ca.com.rlsp.ecommerce.model.StockPurchaseInvoice;
import ca.com.rlsp.ecommerce.model.dto.report.ReportStockPurchaseInvoiceDTO;
import ca.com.rlsp.ecommerce.service.StockPurchaseInvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class StockPurchaseInvoiceController {

    public static final String PURCHASER_MUST_BE_INFORMED_IN_THE_INVOICE = "Legal Person purchaser must be informed in the invoice";
    public static final String INVOICE_ALREADY_SAVED_INTO_DB = "Stock Invoice already saved into DB: ";
    public static final String PAYABLE_INVOICE_MUST_BE_INFORMED = "Trade payable invoice must be informed";
    public static final String COMPANY_MUST_BE_INFORMED = "Ecommerce company must be informed";
    public static final String STOCK_PURCHASE_REMOVED_ID = "Stock Purchase removed. Id: ";
    public static final String NOT_FOUND_STOCK_PURCHASE_WITH_ID = "Not found Stock purchase with id: ";
    private StockPurchaseInvoiceService stockPurchaseInvoiceService;

    public StockPurchaseInvoiceController(StockPurchaseInvoiceService stockPurchaseInvoiceService) {
        this.stockPurchaseInvoiceService = stockPurchaseInvoiceService;
    }

    @ResponseBody
    @PostMapping(value = "/reportStockPurchaseInvoice")
    public ResponseEntity<List<ReportStockPurchaseInvoiceDTO>> reportStockPurchaseInvoice
            (@Valid @RequestBody ReportStockPurchaseInvoiceDTO reportStockPurchaseInvoiceDTO){

        List<ReportStockPurchaseInvoiceDTO> response =
                stockPurchaseInvoiceService.generateReportStockPurchaseInvoice(reportStockPurchaseInvoiceDTO);


        return new ResponseEntity<List<ReportStockPurchaseInvoiceDTO>>(response, HttpStatus.OK);

    }

    @ResponseBody
    @PostMapping(value = "/saveStockPurchaseInvoice")
    public ResponseEntity<StockPurchaseInvoice> saveStockPurchaseInvoice(@RequestBody @Valid StockPurchaseInvoice stockPurchaseInvoice) throws EcommerceException { /*Recebe o JSON e converte pra Objeto*/

        if (stockPurchaseInvoice.getId() == null) {

            if (stockPurchaseInvoice.getDescription() != null) {
                Boolean isIntoDB = stockPurchaseInvoiceService.isIntoDB(stockPurchaseInvoice.getDescription().toUpperCase().trim());

                if(isIntoDB) {
                    throw new EcommerceException(INVOICE_ALREADY_SAVED_INTO_DB + stockPurchaseInvoice.getDescription());
                }
            }
        }

        if (stockPurchaseInvoice.getLegalPersonVendor() == null || stockPurchaseInvoice.getLegalPersonVendor().getId() <= 0) {
            throw new EcommerceException(PURCHASER_MUST_BE_INFORMED_IN_THE_INVOICE);
        }

        if (stockPurchaseInvoice.getEcommerceCompanyPurchaser() == null || stockPurchaseInvoice.getEcommerceCompanyPurchaser().getId() <= 0) {
            throw new EcommerceException(COMPANY_MUST_BE_INFORMED);
        }

        if (stockPurchaseInvoice.getTradePayable() == null || stockPurchaseInvoice.getTradePayable().getId() <= 0) {
            throw new EcommerceException(PAYABLE_INVOICE_MUST_BE_INFORMED);
        }

        StockPurchaseInvoice stockPurchaseInvoiceSaved = stockPurchaseInvoiceService.save(stockPurchaseInvoice);
        return new ResponseEntity<StockPurchaseInvoice>(stockPurchaseInvoiceSaved, HttpStatus.OK);
    }


    @ResponseBody
    @DeleteMapping(value = "/deleteStockPurchaseInvoice/{id}")
    public ResponseEntity<?> deleteStockPurchaseInvoiceById(@PathVariable("id") Long id) {


        stockPurchaseInvoiceService.deleteInvoiceItemProduct(id);/*Delete os filhos*/
        stockPurchaseInvoiceService.deleteById(id); /*Deleta o pai*/

        return new ResponseEntity(STOCK_PURCHASE_REMOVED_ID + id,HttpStatus.OK);
    }


    @ResponseBody
    @GetMapping(value = "/getStockPurchaseInvoice/{id}")
    public ResponseEntity<StockPurchaseInvoice> getStockPurchaseInvoice(@PathVariable("id") Long id) throws EcommerceException {

        StockPurchaseInvoice StockPurchaseInvoice = stockPurchaseInvoiceService.getById(id).orElse(null);

        if (StockPurchaseInvoice == null) {
            throw new EcommerceException(NOT_FOUND_STOCK_PURCHASE_WITH_ID + id);
        }

        return new ResponseEntity<StockPurchaseInvoice>(StockPurchaseInvoice, HttpStatus.OK);
    }


    @ResponseBody
    @GetMapping(value = "/getStockPurchaseInvoiceByDisc/{desc}")
    public ResponseEntity<List<StockPurchaseInvoice>> getStockPurchaseInvoiceByDisc(@PathVariable("desc") String description) {

        List<StockPurchaseInvoice> stockPurchasesInvoice = stockPurchaseInvoiceService.getByDescription(description.toUpperCase().trim());

        return new ResponseEntity<List<StockPurchaseInvoice>>(stockPurchasesInvoice,HttpStatus.OK);
    }
}
