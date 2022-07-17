package ca.com.rlsp.ecommerce.controller;

import ca.com.rlsp.ecommerce.exception.EcommerceException;
import ca.com.rlsp.ecommerce.model.Product;
import ca.com.rlsp.ecommerce.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
public class ProductController {

    private static final String ERROR_GETTING_PRODUCT_BY_ID = "Error getting Product by id/code [RLSP] : ";
    private static final String ERROR_PRODUCT_EXIST_ON_DB = "Product already exist on DB [RLSP] : ";
    private static final String ERROR_ECOMMERCE_COMPANY_MUST_BE_INFORMED= "Ecommerce Company must be informed [RLSP] : ";
    private static final String ERROR_PRODUCT_BRAND_MUST_BE_INFORMED= "Product Brand doesnt must be informed [RLSP] : ";
    private static final String ERROR_PRODUCT_CATEGORY_MUST_BE_INFORMED = "Product Category must be informed [RLSP] : ";

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @GetMapping(value = "/getAllProducts")
    public ResponseEntity<Collection<Product>> getAllProducts(){

        Collection<Product> allProductsSave = productService.getAll();
        return new ResponseEntity<>(allProductsSave, HttpStatus.OK);

    }

    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @GetMapping(value = "/getProduct/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) throws EcommerceException {

        Product getOneProductSaved = productService.getById(productId).orElse(null);

        if(getOneProductSaved == null) {
            throw new EcommerceException(ERROR_GETTING_PRODUCT_BY_ID + productId);
        }
        return new ResponseEntity<>(getOneProductSaved, HttpStatus.OK);
    }

    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @PostMapping(path = "/saveProduct")
    public ResponseEntity<Product> saveProduct(@RequestBody @Valid Product product) throws EcommerceException {

        if(product.getEcommerceCompany() == null && product.getEcommerceCompany().getId() <= 0) {
            throw new EcommerceException(ERROR_ECOMMERCE_COMPANY_MUST_BE_INFORMED );
        }

        if(product.getId() == null) {
            List<Product> productList = productService.getByNameAndEcommerce(product.getName().toUpperCase(), product.getEcommerceCompany().getId());

            if(!productList.isEmpty()) {
                throw new EcommerceException(ERROR_PRODUCT_EXIST_ON_DB + product.getName());
            }
        }

        if(product.getProductBrand() == null && product.getEcommerceCompany().getId() <= 0) {
            throw new EcommerceException(ERROR_PRODUCT_BRAND_MUST_BE_INFORMED );
        }

        if(product.getProductCategory() == null && product.getEcommerceCompany().getId() <= 0) {
            throw new EcommerceException(ERROR_PRODUCT_CATEGORY_MUST_BE_INFORMED);
        }

        Product productSaved  = productService.save(product);
        return new ResponseEntity<>(productSaved, HttpStatus.OK);
    }


    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @DeleteMapping(path = "/deleteProduct")
    public ResponseEntity<?> deleteProduct(@RequestBody Product product){

        productService.deleteById(product.getId());
        return new ResponseEntity<>("Deleted Role Access By Object", HttpStatus.OK);
    }

    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @DeleteMapping(value = "/deleteProduct/{productId}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long productId){

        productService.deleteById(productId);

        return new ResponseEntity<>("Deleted Product By Id", HttpStatus.OK);
    }

    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @GetMapping(value = "/getProduct/byDescription/{description}")
    public ResponseEntity<List<Product>> getByDescriptionRoleAccess(@PathVariable String description){

        List<Product> productList = productService.getByDescription(description.toUpperCase());

        return new ResponseEntity<>(productList, HttpStatus.OK);
    }
}
