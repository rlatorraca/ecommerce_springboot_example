package ca.com.rlsp.ecommerce.repository;

import ca.com.rlsp.ecommerce.model.ProductBrand;
import ca.com.rlsp.ecommerce.model.RoleAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional // Manage all DB transactions
public interface ProductBrandRepository extends JpaRepository<ProductBrand, Long> {

    @Query("SELECT pb FROM ProductBrand pb where upper(trim(pb.description)) like %?1%")
    List<ProductBrand> findDescriptionProductBrand(String description);
}
