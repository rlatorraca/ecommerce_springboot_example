package ca.com.rlsp.ecommerce.controller;


import ca.com.rlsp.ecommerce.exception.EcommerceException;
import ca.com.rlsp.ecommerce.model.ProductCategory;
import ca.com.rlsp.ecommerce.service.ProductCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@RestController
public class ProductCategoryController {

    public static final String ECOMMERCE_HAS_TO_BE_INSERTED = "Ecommerce has to be inserted [RLSP]";
    public static final String CAN_T_INSERT_PRODUCT_CATEGORY_USING_SAME_NAME = "Can't insert product category using same name [RLSP]";
    public static final String DELETED_PRODUCT_CATEGORY = "Deleted Product category [RLSP]";

    public static final String PRODUCT_CATEGORY_DOESNT_EXIST_INTO_DB = "Product category doesn't exist into DB [RLSP]";
    private ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @ResponseBody
    @PostMapping(value = "/saveProductCategory")
    public ResponseEntity<ProductCategory> saveProductCategory(@RequestBody ProductCategory productCategory) throws EcommerceException, MessagingException {

        if(productCategory.getEcommerceCompany() == null || productCategory.getEcommerceCompany().getId() == null){
            throw new EcommerceException(ECOMMERCE_HAS_TO_BE_INSERTED);
        }

        if(productCategory.getId() == null &&
           productCategoryService.isIntoDB(productCategory.getDescription().toUpperCase())){
            throw new EcommerceException(CAN_T_INSERT_PRODUCT_CATEGORY_USING_SAME_NAME);
        }
        ProductCategory productCategorySaved = productCategoryService.save(productCategory);

//        ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
//        productCategoryDTO.setId(productCategory.getId());
//        productCategoryDTO.setDescription(productCategory.getDescription());
//        productCategoryDTO.setEcommerceCompany(productCategory.getEcommerceCompany().getId());

        return new ResponseEntity<ProductCategory>(productCategorySaved, HttpStatus.CREATED);
    }


    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @DeleteMapping(path = "/deleteProductCategory")
    public ResponseEntity<?> deleteProductCategory(@RequestBody ProductCategory productCategory){

        if (!productCategoryService.isPresentIntoDB(productCategory)) {
            return new ResponseEntity(PRODUCT_CATEGORY_DOESNT_EXIST_INTO_DB,HttpStatus.NO_CONTENT);
        }

        productCategoryService.deleteById(productCategory.getId());
        return new ResponseEntity<>(DELETED_PRODUCT_CATEGORY, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/getProductCategoryByDescription/{desc}")
    public ResponseEntity<List<ProductCategory>> getProductCategoryByDescription(@PathVariable("desc") String description) {

        List<ProductCategory> productCategories = productCategoryService.getByDescription(description.toUpperCase());

        return new ResponseEntity<List<ProductCategory>>(productCategories,HttpStatus.OK);
    }

}
