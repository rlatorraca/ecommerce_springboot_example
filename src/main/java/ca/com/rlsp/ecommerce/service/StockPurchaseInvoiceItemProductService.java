package ca.com.rlsp.ecommerce.service;

import ca.com.rlsp.ecommerce.model.Product;
import ca.com.rlsp.ecommerce.model.StockPurchaseInvoiceItemProduct;
import ca.com.rlsp.ecommerce.repository.StockPurchaseInvoiceItemProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockPurchaseInvoiceItemProductService {

    private StockPurchaseInvoiceItemProductRepository stockPurchaseInvoiceItemProductRepository;

    public StockPurchaseInvoiceItemProductService(StockPurchaseInvoiceItemProductRepository stockPurchaseInvoiceItemProductRepository) {
        this.stockPurchaseInvoiceItemProductRepository = stockPurchaseInvoiceItemProductRepository;
    }

    public StockPurchaseInvoiceItemProduct save(StockPurchaseInvoiceItemProduct stockPurchaseInvoiceItemProduct) {
        return stockPurchaseInvoiceItemProductRepository.save(stockPurchaseInvoiceItemProduct);
    }

    public List<StockPurchaseInvoiceItemProduct> existStockPurchaseInvoiceItemByProductAndByInvoice(Long productId, Long invoiceId) {
        return stockPurchaseInvoiceItemProductRepository.fetchStockPurchaseInvoiceItemByProductAndByInvoice(productId, invoiceId);
    }

    public Optional<StockPurchaseInvoiceItemProduct> getById(Long stockPurchaseInvoiceItemProductId) {

        return Optional.ofNullable(stockPurchaseInvoiceItemProductRepository.findById(stockPurchaseInvoiceItemProductId).orElse(null));
    }

    public void deleteById(Long id) {
        stockPurchaseInvoiceItemProductRepository.deleteByStockPurchaseInvoiceId(id);
    }

    public boolean existStockPurchaseInvoiceItemProduct(Long id) {
        return stockPurchaseInvoiceItemProductRepository.existStockPurchaseInvoiceItemProduct(id);
    }
}
