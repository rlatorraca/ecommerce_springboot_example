package ca.com.rlsp.ecommerce.repository;

import ca.com.rlsp.ecommerce.model.StockPurchaseInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface StockPurchaseInvoiceRepository extends JpaRepository<StockPurchaseInvoice, Long> {

    @Query("SELECT pse FROM StockPurchaseInvoice pse where upper(trim(pse.description)) like %?1%")
    List<StockPurchaseInvoice> getInvoiceByDescription(String description);


    @Query("SELECT pse FROM StockPurchaseInvoice pse where pse.legalPersonVendor.id = ?1")
    List<StockPurchaseInvoice> getInvoiceByPerson(Long personId);


    @Query("SELECT pse FROM StockPurchaseInvoice pse where pse.tradePayable.id = ?1")
    List<StockPurchaseInvoice> getInvoiceByTradePayable(Long tradePayableId);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(nativeQuery = true, value = "delete from invoice_item_product where product_id = ?1")
    void deleteInvoiceItemProduct(Long productId);
}
