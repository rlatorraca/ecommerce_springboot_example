package ca.com.rlsp.ecommerce.service;

import ca.com.rlsp.ecommerce.model.ProductBrand;
import ca.com.rlsp.ecommerce.model.ProductCategory;
import ca.com.rlsp.ecommerce.model.RoleAccess;
import ca.com.rlsp.ecommerce.repository.ProductBrandRepository;
import ca.com.rlsp.ecommerce.repository.RoleAccessRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ProductBrandService {

    private final ProductBrandRepository productBrandRepository;


    public ProductBrandService(ProductBrandRepository productBrandRepository) {
        this.productBrandRepository = productBrandRepository;
    }

    public Collection<ProductBrand> getAll() {

        return productBrandRepository.findAll();
    }

    public Optional<ProductBrand> getById(Long productCategoryId) {

        return Optional.ofNullable(productBrandRepository.findById(productCategoryId).orElse(null));
    }

    public ProductBrand save(ProductBrand productCategory) {

        return productBrandRepository.save(productCategory);
    }


    public void deleteById(Long productCategoryId) {

        productBrandRepository.deleteById(productCategoryId);
    }

    public List<ProductBrand> getByDescription(String description) {
        return productBrandRepository.findDescriptionProductBrand((description));
    }
}
