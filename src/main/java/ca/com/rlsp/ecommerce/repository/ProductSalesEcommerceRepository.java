package ca.com.rlsp.ecommerce.repository;

import ca.com.rlsp.ecommerce.model.ProductSalesEcommerce;
import ca.com.rlsp.ecommerce.model.SalesInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
@Transactional
public interface ProductSalesEcommerceRepository extends JpaRepository<ProductSalesEcommerce, Long> {
}
