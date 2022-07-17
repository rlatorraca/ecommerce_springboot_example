package ca.com.rlsp.ecommerce.service;

import ca.com.rlsp.ecommerce.model.ProductCategory;
import ca.com.rlsp.ecommerce.repository.ProductCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService {

    private ProductCategoryRepository productCategoryRepository;

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    public ProductCategory save(ProductCategory productCategory){
        return productCategoryRepository.save(productCategory);
    }

    public Boolean isIntoDB(String description){
        return productCategoryRepository.isProductCategoryIntoDB(description);
    }

    public void deleteById(Long id) {
        productCategoryRepository.deleteById(id);
    }

   public boolean isPresentIntoDB(ProductCategory productCategory) {
        return productCategoryRepository.findById(productCategory.getId()).isPresent() == true;
    }

    public List<ProductCategory> getByDescription(String description) {
        return productCategoryRepository.getProductCategoryByDescription(description);
    }
}
