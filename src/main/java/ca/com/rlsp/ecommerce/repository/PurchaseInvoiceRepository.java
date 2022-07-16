package ca.com.rlsp.ecommerce.repository;

import ca.com.rlsp.ecommerce.model.ProductSalesEcommerce;
import ca.com.rlsp.ecommerce.model.PurchaseInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PurchaseInvoiceRepository extends JpaRepository<ProductSalesEcommerce, Long> {

    @Query("SELECT pse FROM PurchaseInvoice pse where upper(trim(pse.description)) like %?1%")
    List<PurchaseInvoice> getInvoiceByDescription(String description);


    @Query("SELECT pse FROM PurchaseInvoice pse where pse.person.id = ?1")
    List<PurchaseInvoice> getInvoiceByPerson(Long personId);


    @Query("SELECT pse FROM PurchaseInvoice pse where pse.tradePayable.id = ?1")
    List<PurchaseInvoice> getInvoiceByTradePayable(Long tradePayableId);


    @Query("SELECT pse FROM PurchaseInvoice pse where pse.ecommerceCompany.id = ?1")
    List<PurchaseInvoice> getInvoiceByEcommerce(Long ecommerceId);

}
