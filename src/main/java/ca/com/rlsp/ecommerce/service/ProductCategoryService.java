package ca.com.rlsp.ecommerce.service;

import ca.com.rlsp.ecommerce.model.ProductCategory;
import ca.com.rlsp.ecommerce.repository.ProductCategoryRepository;

public class ProductCategoryService {

    private ProductCategoryRepository productCategoryRepository;

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    public ProductCategory saveProductCategory(ProductCategory productCategory){

        return productCategoryRepository.save(productCategory);
    }
}
