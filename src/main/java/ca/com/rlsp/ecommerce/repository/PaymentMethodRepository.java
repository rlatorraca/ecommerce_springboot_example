package ca.com.rlsp.ecommerce.repository;

import ca.com.rlsp.ecommerce.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

    @Query(value = "select pm from PaymentMethod pm where pm.ecommerceCompany.id = ?1")
    List<PaymentMethod> findAllPaymentsMethodByCompanyId(Long ecommerceid);
}
