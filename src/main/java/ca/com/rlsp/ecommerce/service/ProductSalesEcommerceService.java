package ca.com.rlsp.ecommerce.service;

import ca.com.rlsp.ecommerce.model.ProductImage;
import ca.com.rlsp.ecommerce.model.ProductSalesEcommerce;
import ca.com.rlsp.ecommerce.repository.ProductSalesEcommerceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductSalesEcommerceService {

    private ProductSalesEcommerceRepository productSalesEcommerceRepository;

    public ProductSalesEcommerceService(ProductSalesEcommerceRepository productSalesEcommerceRepository) {
        this.productSalesEcommerceRepository = productSalesEcommerceRepository;
    }

    public ProductSalesEcommerce findById(Long saleId) {
        return productSalesEcommerceRepository.findById(saleId).orElse(new ProductSalesEcommerce());
    }

    public ProductSalesEcommerce saveAndFlush(ProductSalesEcommerce productSalesEcommerce) {

        return productSalesEcommerceRepository.saveAndFlush(productSalesEcommerce);
    }
}
