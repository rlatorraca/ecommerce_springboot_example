package ca.com.rlsp.ecommerce.repository;

import ca.com.rlsp.ecommerce.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    @Query("SELECT pi FROM ProductImage pi where pi.product.id = ?1")
    List<ProductImage> fetchProductImages(Long productId);

    @Transactional
    @Modifying(flushAutomatically = true)
    @Query(nativeQuery = true, value = "DELETE FROM product_image WHERE id = ?1")
    void deleteAllImages(Long productId);

    @Query(value = "SELECT COUNT(1) > 0 FROM product_image WHERE id = productImageId ", nativeQuery = true)
    Boolean isImageSavedIntoDB(Long productImageId);
}
