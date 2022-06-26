package ca.com.rlsp.ecommerce.repository;

import ca.com.rlsp.ecommerce.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    @Query(value = "SELECT COUNT(1) > 0 FROM product_category WHERE upper(trim(description)) = upper(trim(?1)) ", nativeQuery = true)
    public Boolean isProductCategoryIntoDB(String productCategory);

    @Query(value = "SELECT pc FROM ProductCategory pc WHERE upper(trim(pc.description)) like %?1% ")
    List<ProductCategory> getProductCategoryByDescription(String description);
}
