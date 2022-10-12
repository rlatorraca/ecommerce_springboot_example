package ca.com.rlsp.ecommerce.controller;


import ca.com.rlsp.ecommerce.exception.EcommerceException;
import ca.com.rlsp.ecommerce.model.SalesInvoice;
import ca.com.rlsp.ecommerce.service.SalesInvoiceService;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RepositoryRestController
public class SalesInvoiceController {

    private SalesInvoiceService salesInvoiceservice;

    public SalesInvoiceController(SalesInvoiceService salesInvoiceservice) {
        this.salesInvoiceservice = salesInvoiceservice;
    }




    @ResponseBody
    @GetMapping(value = "/getSalesInvoiceBySaleId/{salesId}")
    public ResponseEntity<List<SalesInvoice>> getSalesInvoiceBySaleId(@PathVariable("salesId") Long salesId) throws EcommerceException {

        List<SalesInvoice> listSalesInvoice = salesInvoiceservice.getSalesInvoiceBySaleId(salesId);

        if (listSalesInvoice == null) {
            throw new EcommerceException("Sales Invoice NOT found [RLSP] : id " + salesId);
        }

        return new ResponseEntity<List<SalesInvoice>>(listSalesInvoice, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/getSalesInvoiceBySaleIdUnique/{salesId}")
    public ResponseEntity<SalesInvoice> getSalesInvoiceBySaleIdUnique(@PathVariable("salesId") Long salesId) throws EcommerceException {

        SalesInvoice saleInvoice = salesInvoiceservice.getSalesInvoiceBySaleIdUnique(salesId);

        if (saleInvoice == null) {
            throw new EcommerceException("Sales Invoice NOT found [RLSP] : id " + salesId);
        }

        return new ResponseEntity<SalesInvoice>(saleInvoice, HttpStatus.OK);
    }
}
