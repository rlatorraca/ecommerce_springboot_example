package ca.com.rlsp.ecommerce.controller;

import ca.com.rlsp.ecommerce.model.ProductImage;
import ca.com.rlsp.ecommerce.model.dto.ProductImageDTO;
import ca.com.rlsp.ecommerce.service.ProductImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductImageController {


    public static final String ALL_PRODUCT_IMAGES_WERE_REMOVED = "All Product Images were removed";
    public static final String IMAGE_ALREADY_REMOVED_OR_NOT_SAVED_BY_ID = "Image already removed or not saved by id: ";
    public static final String IMAGE_REMOVED = "Image Removed";
    private ProductImageService productImageService;

    public ProductImageController(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    @ResponseBody
    @PostMapping(value = "/saveProductImage")
    public ResponseEntity<ProductImageDTO> saveProductImage(@RequestBody ProductImage productImage){

        productImage = productImageService.saveAndFlush(productImage);


        // Use a DTO to avoid a recursive response
        ProductImageDTO productImageDTO = new ProductImageDTO();
        productImageDTO.setId(productImage.getId());
        productImageDTO.setEcommerceCompany(productImage.getEcommerceCompany().getId());
        productImageDTO.setProduct(productImage.getProduct().getId());
        productImageDTO.setThumbnail(productImage.getThumbnail());
        productImageDTO.setSourceImage(productImage.getSourceImage());

        return new ResponseEntity<ProductImageDTO>(productImageDTO, HttpStatus.CREATED);

    }



    @ResponseBody
    @DeleteMapping(value = "/deleteAllProductImages/{productId}")
    public ResponseEntity<?> deleteAllProductImages(@PathVariable("productId") Long productId) {

        if(!productImageService.isImageSavedByProductIdIntoDB(productId)) {
            return new ResponseEntity<String>(IMAGE_ALREADY_REMOVED_OR_NOT_SAVED_BY_ID + productId, HttpStatus.OK);
        }
        productImageService.deleteAllProductImages(productId);

        return new ResponseEntity<String>(ALL_PRODUCT_IMAGES_WERE_REMOVED, HttpStatus.NO_CONTENT);
    }



    @ResponseBody
    @DeleteMapping(value = "/deleteProductImage")
    public ResponseEntity<?> deleteProductImageByObject(@RequestBody ProductImage productImage) {

        if(!productImageService.isImageSavedByProductImageIdIntoDB(productImage.getId())) {
            return new ResponseEntity<String>(IMAGE_ALREADY_REMOVED_OR_NOT_SAVED_BY_ID + productImage.getId(), HttpStatus.OK);
        }

        productImageService.deleteProductImageById(productImage.getId());

        return new ResponseEntity<String>(IMAGE_REMOVED, HttpStatus.NO_CONTENT);
    }



    @ResponseBody
    @DeleteMapping(value = "/deleteProductImageById/{productImageId}")
    public ResponseEntity<?> deleteProductImageById(@PathVariable("productImageId") Long id) {

        if(!productImageService.isImageSavedByProductImageIdIntoDB(id)) {
            return new ResponseEntity<String>(IMAGE_ALREADY_REMOVED_OR_NOT_SAVED_BY_ID + id, HttpStatus.OK);
        }

        productImageService.deleteProductImageById(id);

        return new ResponseEntity<String>(IMAGE_REMOVED, HttpStatus.NO_CONTENT);
    }

    @ResponseBody
    @GetMapping(value = "/getProductImageByProduct/{productId}")
    public ResponseEntity<?> getProductImageByProduct(@PathVariable("productId") Long productId){

        if(!productImageService.isImageSavedByProductIdIntoDB(productId)) {
            return new ResponseEntity<String>(IMAGE_ALREADY_REMOVED_OR_NOT_SAVED_BY_ID + productId, HttpStatus.OK);
        }

        List<ProductImageDTO> dtos = new ArrayList<ProductImageDTO>();

        List<ProductImage> productImages = productImageService.fetchAllProductImagesByProduct(productId);

        for (ProductImage productImage : productImages) {

            ProductImageDTO productImageDTO = new ProductImageDTO();
            productImageDTO.setId(productImage.getId());
            productImageDTO.setEcommerceCompany(productImage.getEcommerceCompany().getId());
            productImageDTO.setProduct(productImage.getProduct().getId());
            productImageDTO.setThumbnail(productImage.getThumbnail());
            productImageDTO.setSourceImage(productImage.getSourceImage());

            dtos.add(productImageDTO);
        }

        return new ResponseEntity<List<ProductImageDTO>>(dtos, HttpStatus.OK);

    }
}
