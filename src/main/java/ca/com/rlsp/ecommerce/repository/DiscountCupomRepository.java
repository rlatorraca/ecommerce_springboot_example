package ca.com.rlsp.ecommerce.repository;

import ca.com.rlsp.ecommerce.model.DiscountCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountCupomRepository extends JpaRepository<DiscountCoupon, Long> {

    @Query(value = "SELECT dc from DiscountCoupon dc where dc.ecommerceCompany.id = ?1")
    public List<DiscountCoupon> discountCouponByEcommerce(Long idEmpresa);
}
