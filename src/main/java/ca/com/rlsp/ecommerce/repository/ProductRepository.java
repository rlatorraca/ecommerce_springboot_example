package ca.com.rlsp.ecommerce.repository;

import ca.com.rlsp.ecommerce.model.Product;
import ca.com.rlsp.ecommerce.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT COUNT(1) > 0 FROM Product WHERE upper(trim(nome)) = upper(trim(?1)) ", nativeQuery = true)
    public Boolean isProductIntoDB(String product);


    @Query(value = "SELECT p FROM Product p WHERE upper(trim(p.description)) like %?1% ")
    List<Product> getProductByDescription(String description);

    @Query(value = "SELECT p FROM Product p WHERE upper(trim(p.name)) like %?1% ")
    List<Product> getProductByName(String name);

    @Query(value = "SELECT p FROM Product p WHERE upper(trim(p.name)) like %?1% and p.ecommerceCompany.id = ?2")
    List<Product> getProductByNameAndEcommerceCompany(String name, Long ecommerceId);
}
