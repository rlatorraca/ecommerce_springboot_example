package ca.com.rlsp.ecommerce.controller;

import ca.com.rlsp.ecommerce.exception.EcommerceException;
import ca.com.rlsp.ecommerce.model.Product;
import ca.com.rlsp.ecommerce.model.StockPurchaseInvoiceItemProduct;
import ca.com.rlsp.ecommerce.service.StockPurchaseInvoiceItemProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class StockPurchaseInvoiceItemProductController {

    public static final String PRODUCT_ALREADY_SAVED_IN_A_PURCHASE_INVOICE_RLSP = "Product already saved in a Purchase Invoice [RLSP]";
    public static final String YOU_MUST_INFORM_A_EXISTING_PRODUCT_ID_RLSP = "You must inform aProduct Id [RLSP]";
    public static final String YOU_MUST_INFORM_A_EXISTING_PURCHASE_INVOICE_ID_RLSP = "You must inform a Purchase Invoice Id [RLSP]";
    public static final String YOU_MUST_INFORM_A_EXISTING_ECOMMERCE_ID_RLSP = "You must inform a Ecommerce Id [RLSP]";
    public static final String DELETED_STOCK_PURCHASE_INVOICE_FOR_ITEM_PRODUCT_BY_OBJECT_RLSP = "Deleted Stock Purchase Invoice for Item Product by Object [RLSP]";
    private static final String STOCK_PURCHASE_INVOICE_DOESNT_EXIST_INTO_DB = "Stock purchase invoice doesnt exist into DB [RLSP]";
    StockPurchaseInvoiceItemProductService stockPurchaseInvoiceItemProductService;

    public StockPurchaseInvoiceItemProductController(StockPurchaseInvoiceItemProductService stockPurchaseInvoiceItemProductService) {
        this.stockPurchaseInvoiceItemProductService = stockPurchaseInvoiceItemProductService;
    }

    @ResponseBody
    @PostMapping("/saveStockPurchaseInvoiceItemProduct")
    public ResponseEntity<StockPurchaseInvoiceItemProduct> saveStockPurchaseInvoiceItemProduct(@RequestBody @Valid StockPurchaseInvoiceItemProduct stockPurchaseInvoiceItemProduct)
                                                                throws EcommerceException {


        if (stockPurchaseInvoiceItemProduct.getId() != null) {
            if (stockPurchaseInvoiceItemProduct.getProduct() == null || stockPurchaseInvoiceItemProduct.getProduct().getId() < 1) {
                throw new EcommerceException(YOU_MUST_INFORM_A_EXISTING_PRODUCT_ID_RLSP);
            }

            if (stockPurchaseInvoiceItemProduct.getStockPurchaseinvoice() == null || stockPurchaseInvoiceItemProduct.getStockPurchaseinvoice().getId() < 1) {
                throw new EcommerceException(YOU_MUST_INFORM_A_EXISTING_PURCHASE_INVOICE_ID_RLSP);
            }

            if (stockPurchaseInvoiceItemProduct.getEcommerceCompany() == null || stockPurchaseInvoiceItemProduct.getEcommerceCompany().getId() < 1) {
                throw new EcommerceException(YOU_MUST_INFORM_A_EXISTING_ECOMMERCE_ID_RLSP);
            }
            List<StockPurchaseInvoiceItemProduct>
                    stockPurchaseInvoiceExist = stockPurchaseInvoiceItemProductService
                                                     .existStockPurchaseInvoiceItemByProductAndByInvoice(
                                                            stockPurchaseInvoiceItemProduct.getProduct().getId(),
                                                            stockPurchaseInvoiceItemProduct.getStockPurchaseinvoice().getId());

            if (!stockPurchaseInvoiceExist.isEmpty()) {
                throw new EcommerceException(PRODUCT_ALREADY_SAVED_IN_A_PURCHASE_INVOICE_RLSP);
            }



        }
        StockPurchaseInvoiceItemProduct stockPurchaseInvoiceItemProductSaved = stockPurchaseInvoiceItemProductService.save(stockPurchaseInvoiceItemProduct);
        stockPurchaseInvoiceItemProductSaved = stockPurchaseInvoiceItemProductService.getById(stockPurchaseInvoiceItemProductSaved.getId()).get();

        return new ResponseEntity<StockPurchaseInvoiceItemProduct>(stockPurchaseInvoiceItemProductSaved, HttpStatus.CREATED);


    }

    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @DeleteMapping(path = "/deleteStockPurchaseInvoiceItemProduct")
    public ResponseEntity<?> deleteStockPurchaseInvoiceItemProduct(@RequestBody StockPurchaseInvoiceItemProduct stockPurchaseInvoiceItemProduct){

        if (!stockPurchaseInvoiceItemProductService.existStockPurchaseInvoiceItemProduct(stockPurchaseInvoiceItemProduct.getId())) {
            return new ResponseEntity(STOCK_PURCHASE_INVOICE_DOESNT_EXIST_INTO_DB,HttpStatus.OK);
        }

        stockPurchaseInvoiceItemProductService.deleteById(stockPurchaseInvoiceItemProduct.getId());
        return new ResponseEntity<>(DELETED_STOCK_PURCHASE_INVOICE_FOR_ITEM_PRODUCT_BY_OBJECT_RLSP, HttpStatus.NO_CONTENT);
    }

    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @DeleteMapping(value = "/deleteStockPurchaseInvoiceItemProduct/{stockPurchaseInvoiceItemProductId}")
    public ResponseEntity<?> deleteStockPurchaseInvoiceItemProduct(@PathVariable Long stockPurchaseInvoiceItemProductId){

        if (!stockPurchaseInvoiceItemProductService.existStockPurchaseInvoiceItemProduct(stockPurchaseInvoiceItemProductId)) {
            return new ResponseEntity(STOCK_PURCHASE_INVOICE_DOESNT_EXIST_INTO_DB,HttpStatus.NO_CONTENT);
        }

        stockPurchaseInvoiceItemProductService.deleteById(stockPurchaseInvoiceItemProductId);

        return new ResponseEntity<>("Deleted Stock Purchase Invoice for Item Product by Id [RLSP]", HttpStatus.NO_CONTENT);
    }
}
