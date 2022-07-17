package ca.com.rlsp.ecommerce.repository;

import ca.com.rlsp.ecommerce.model.StockPurchaseInvoiceItemProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface StockPurchaseInvoiceItemProductRepository extends JpaRepository<StockPurchaseInvoiceItemProduct, Long> {

    // return a invoice having a specific product
    @Query("SELECT spiip FROM StockPurchaseInvoiceItemProduct spiip WHERE spiip.product.id = ?1 AND spiip.stockPurchaseInvoice.id = ?2")
    StockPurchaseInvoiceItemProduct getStockPurchaseInvoiceItemByProductAndByInvoice(Long productId, Long invoiceId);


    // get a list of invoices that have a specific product
    @Query("SELECT spiip FROM StockPurchaseInvoiceItemProduct spiip WHERE spiip.product.id = ?1")
    List<StockPurchaseInvoiceItemProduct> getStockPurchaseInvoiceItemProductByProduct(Long productId);


    // return a invoice
    @Query("SELECT spiip FROM StockPurchaseInvoiceItemProduct spiip WHERE spiip.stockPurchaseInvoice.id = ?1")
    StockPurchaseInvoiceItemProduct getStockPurchaseInvoiceItemProductByInvoice(Long invoiceId);


    // returna a list of invoices by Ecommerce (company)
    @Query("SELECT spiip FROM StockPurchaseInvoiceItemProduct spiip WHERE spiip.ecommerceCompany.id = ?1")
    List<StockPurchaseInvoiceItemProduct> getStockPurchaseInvoiceItemProductByEcommerce(Long ecommerceId);


    // delete a selected invoice
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM stock_purchase_invoice_item_product WHERE id = ?1")
    void deleteByStockPurchaseInvoiceId(Long id);
}
