package ca.com.rlsp.ecommerce.service;

import ca.com.rlsp.ecommerce.model.ProductCategory;
import ca.com.rlsp.ecommerce.model.ProductImage;
import ca.com.rlsp.ecommerce.model.RoleAccess;
import ca.com.rlsp.ecommerce.model.StockPurchaseInvoice;
import ca.com.rlsp.ecommerce.repository.RoleAccessRepository;
import ca.com.rlsp.ecommerce.repository.StockPurchaseInvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class StockPurchaseInvoiceService {

    private StockPurchaseInvoiceRepository stockPurchaseInvoiceRepository;

    public StockPurchaseInvoiceService(StockPurchaseInvoiceRepository stockPurchaseInvoiceRepository) {
        this.stockPurchaseInvoiceRepository = stockPurchaseInvoiceRepository;
    }


    public Collection<StockPurchaseInvoice> getAll() {

        return stockPurchaseInvoiceRepository.findAll();
    }

    public Optional<StockPurchaseInvoice> getById(Long roleAccessId) {

        return Optional.ofNullable(stockPurchaseInvoiceRepository.findById(roleAccessId).orElse(null));
    }

    public StockPurchaseInvoice save(StockPurchaseInvoice stockPurchaseInvoice) {

        return stockPurchaseInvoiceRepository.save(stockPurchaseInvoice);
    }

    public StockPurchaseInvoice saveAndFlush(StockPurchaseInvoice stockPurchaseInvoice) {

        return stockPurchaseInvoiceRepository.saveAndFlush(stockPurchaseInvoice);
    }


    public void deleteById(Long stockPurchaseInvoiceId) {

        stockPurchaseInvoiceRepository.deleteById(stockPurchaseInvoiceId);
    }

    public List<StockPurchaseInvoice> getByDescription(String description) {
        return stockPurchaseInvoiceRepository.getInvoiceByDescription((description));
    }

    public boolean isPresentIntoDB(StockPurchaseInvoice stockPurchaseInvoice) {
        return stockPurchaseInvoiceRepository.findById(stockPurchaseInvoice.getId()).isPresent() == true;
    }

    public Boolean isIntoDB(String description){
        return !stockPurchaseInvoiceRepository.getInvoiceByDescription(description).isEmpty();
    }

    public void deleteInvoiceItemProduct(Long purchaseInvoiceId){
        stockPurchaseInvoiceRepository.deleteInvoiceItemProduct(purchaseInvoiceId);
    }
}
