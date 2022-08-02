package ca.com.rlsp.ecommerce.repository;

import ca.com.rlsp.ecommerce.model.SalesInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesInvoiceRepository extends JpaRepository<SalesInvoice, Long> {
}
