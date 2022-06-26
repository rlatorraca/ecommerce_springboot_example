package ca.com.rlsp.ecommerce.service;

import ca.com.rlsp.ecommerce.model.ProductCategory;
import ca.com.rlsp.ecommerce.repository.ProductCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryService {

    private ProductCategoryRepository productCategoryRepository;

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    public ProductCategory saveProductCategory(ProductCategory productCategory){
        return productCategoryRepository.save(productCategory);
    }

    public Boolean isProductCategoryIntoDB(String productCategory){
        return productCategoryRepository.isProductCategoryIntoDB(productCategory);
    }

    public void deleteProductCategoryById(Long id) {
        productCategoryRepository.deleteById(id);
    }

   public boolean isProductCategoryPresentIntoDB(ProductCategory productCategory) {
        return productCategoryRepository.findById(productCategory.getId()).isPresent() == true;
    }

    public List<ProductCategory> getProductCategoryByDescription(String description) {
        return productCategoryRepository.getProductCategoryByDescription(description);
    }
}
