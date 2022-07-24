package ca.com.rlsp.ecommerce.service;

import ca.com.rlsp.ecommerce.model.Product;
import ca.com.rlsp.ecommerce.model.ProductImage;
import ca.com.rlsp.ecommerce.repository.ProductImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageService {

    private ProductImageRepository productImageRepository;

    public ProductImage save(ProductImage productImage) {

        return productImageRepository.save(productImage);
    }

    public ProductImage saveAndFlush(ProductImage productImage) {

        return productImageRepository.saveAndFlush(productImage);
    }

    public void deleteAllProductImages(Long productId) {
        productImageRepository.deleteAllImages(productId);
    }

    public void deleteProductImageById(Long productImageById) {
        productImageRepository.deleteById(productImageById);
    }


    public Boolean existsImageSavedById(Long productImageId){
        return productImageRepository.isImageSavedIntoDB(productImageId);
    }


    public List<ProductImage> fetchProductImages(Long productImageId) {
        return productImageRepository.fetchProductImages(productImageId);
    }

}
