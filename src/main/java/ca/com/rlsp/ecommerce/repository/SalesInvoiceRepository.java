package ca.com.rlsp.ecommerce.repository;

import ca.com.rlsp.ecommerce.model.SalesInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SalesInvoiceRepository extends JpaRepository<SalesInvoice, Long> {

    @Query(value = "SELECT si FROM SalesInvoice si WHERE si.productSalesEcommerce.id= ?1")
    List<SalesInvoice> getSaleInoviceBySaleId(Long saleId);

    @Query(value = "SELECT si FROM SalesInvoice si WHERE si.productSalesEcommerce.id = ?1")
    SalesInvoice getSaleInvoiceBySaleIdUnique(Long saleId);

}
