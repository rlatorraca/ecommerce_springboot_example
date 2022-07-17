package ca.com.rlsp.ecommerce.service;

import ca.com.rlsp.ecommerce.repository.StockPurchaseInvoiceRepository;
import org.springframework.stereotype.Service;

@Service
public class StockPurchaseInvoiceItemProductService {

    private StockPurchaseInvoiceRepository stockPurchaseInvoiceRepository;

    public StockPurchaseInvoiceItemProductService(StockPurchaseInvoiceRepository stockPurchaseInvoiceRepository) {
        this.stockPurchaseInvoiceRepository = stockPurchaseInvoiceRepository;
    }
}
