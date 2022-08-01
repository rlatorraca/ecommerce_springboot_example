package ca.com.rlsp.ecommerce.service;

import ca.com.rlsp.ecommerce.model.ProductImage;
import ca.com.rlsp.ecommerce.model.ProductSalesEcommerce;
import ca.com.rlsp.ecommerce.repository.ProductSalesEcommerceRepository;

public class ProductSalesEcommerceService {

    private ProductSalesEcommerceRepository productSalesEcommerceRepository;

    public ProductSalesEcommerce saveAndFlush(ProductSalesEcommerce productSalesEcommerce) {

        return productSalesEcommerceRepository.saveAndFlush(productSalesEcommerce);
    }
}
