package ca.com.rlsp.ecommerce.service;

import ca.com.rlsp.ecommerce.model.Product;
import ca.com.rlsp.ecommerce.model.ProductImage;
import ca.com.rlsp.ecommerce.repository.ProductImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageService {

    private ProductImageRepository productImageRepository;

    public ProductImageService(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

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


    public Boolean isImageSavedByProductIdIntoDB(Long productId){
        return productImageRepository.isImageSavedByProductIntoDB(productId);
    }
    public Boolean isImageSavedByProductImageIdIntoDB(Long productImageId){
        return productImageRepository.isImageSavedByProductImageIdIntoDB(productImageId);
    }


    public List<ProductImage> fetchAllProductImagesByProduct(Long productId) {
        return productImageRepository.fetchAllProductImagesByProduct(productId);
    }

}
