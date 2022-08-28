package ca.com.rlsp.ecommerce.service;

import ca.com.rlsp.ecommerce.model.SalesInvoice;
import ca.com.rlsp.ecommerce.repository.SalesInvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesInvoiceService {

    private SalesInvoiceRepository salesInvoiceRepository;

    public SalesInvoiceService(SalesInvoiceRepository salesInvoiceRepository) {
        this.salesInvoiceRepository = salesInvoiceRepository;
    }


    public List<SalesInvoice> getSalesInvoiceBySaleId(Long salesId) {

        return salesInvoiceRepository.getSaleInoviceBySaleId(salesId);
    }

    public SalesInvoice getSalesInvoiceBySaleIdUnique(Long salesId) {

        return salesInvoiceRepository.getSaleInvoiceBySaleIdUnique(salesId);
    }
}
