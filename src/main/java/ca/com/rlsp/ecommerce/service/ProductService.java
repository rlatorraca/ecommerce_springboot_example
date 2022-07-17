package ca.com.rlsp.ecommerce.service;

import ca.com.rlsp.ecommerce.model.Product;
import ca.com.rlsp.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(Product Product) {

        return productRepository.save(Product);
    }

    public Boolean isProductCategoryIntoDB(String productCategory){
        return productRepository.isProductIntoDB(productCategory);
    }

    public Optional<Product> getById(Long roleAccessId) {

        return Optional.ofNullable(productRepository.findById(roleAccessId).orElse(null));
    }
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

   public boolean isProductPresentIntoDB(Product productCategory) {
        return productRepository.findById(productCategory.getId()).isPresent() == true;
    }

    public List<Product> getByDescription(String description) {
        return productRepository.getProductByDescription(description);
    }

    public List<Product> getProductByName(String name) {
        return productRepository.getProductByName(name);
    }

    public List<Product> getByNameAndEcommerce(String productCategory, Long ecommerceId){
        return productRepository.getProductByNameAndEcommerceCompany(productCategory, ecommerceId);
    }

    public Collection<Product> getAll() {

        return productRepository.findAll();
    }
}
