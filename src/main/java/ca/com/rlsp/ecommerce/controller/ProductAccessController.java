package ca.com.rlsp.ecommerce.controller;

import ca.com.rlsp.ecommerce.exception.EcommerceException;
import ca.com.rlsp.ecommerce.model.ProductBrand;
import ca.com.rlsp.ecommerce.model.RoleAccess;
import ca.com.rlsp.ecommerce.service.ProductBrandService;
import ca.com.rlsp.ecommerce.service.RoleAccessService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
public class ProductAccessController {

    private static final String ERROR_GETTING_PRODUCT_BRAND_BY_ID = "Error getting Product Brand by id/code [RLSP] : ";
    private static final String ERROR_PRODUCT_BRAND_EXIST_ON_DB = "Product Brand already exist on DB [RLSP] : ";
    private final ProductBrandService productBrandService;

    public ProductAccessController(ProductBrandService productBrandService) {
        this.productBrandService = productBrandService;
    }

    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @GetMapping(value = "/getAllProductBrand")
    public ResponseEntity<Collection<ProductBrand>> getAllProductBrand(){

        Collection<ProductBrand> allRolesSaved = productBrandService.getAll();
        return new ResponseEntity<>(allRolesSaved, HttpStatus.OK);

    }

    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @GetMapping(value = "/productBrand/{productBrandId}")
    public ResponseEntity<ProductBrand> getByIdProductBrand(@PathVariable Long productBrandId) throws EcommerceException {

        ProductBrand getOneRolesSaved = productBrandService.getById(productBrandId).orElse(null);

        if(getOneRolesSaved == null) {
            throw new EcommerceException(ERROR_GETTING_PRODUCT_BRAND_BY_ID + productBrandId);
        }
        return new ResponseEntity<>(getOneRolesSaved, HttpStatus.OK);
    }

    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @PostMapping(path = "/saveProductBrand")
    public ResponseEntity<ProductBrand> saveProductBrand(@RequestBody @Valid ProductBrand productBrand) throws EcommerceException {

        if(productBrand.getId() == null) {
            List<ProductBrand> productBrandList = productBrandService.getByDescription(productBrand.getDescription().toUpperCase());

            if(!productBrandList.isEmpty()) {
                throw new EcommerceException(ERROR_PRODUCT_BRAND_EXIST_ON_DB + productBrand.getDescription());
            }
        }
        ProductBrand productBrandSaved  = productBrandService.save(productBrand);
        return new ResponseEntity<>(productBrandSaved, HttpStatus.CREATED);
    }


    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @PostMapping(path = "/deleteProductBrand")
    public ResponseEntity<?> deleteObjectProductBrand(@RequestBody ProductBrand productBrand){

        productBrandService.deleteById(productBrand.getId());
        return new ResponseEntity<>("Deleted Product Brand By Object", HttpStatus.OK);
    }

    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @DeleteMapping(value = "/deleteProductBrand/{productBrandId}")
    public ResponseEntity<?> deleteByIdProductBrand(@PathVariable Long productBrandId){

        productBrandService.deleteById(productBrandId);

        return new ResponseEntity<>("Deleted Product Brand By Id", HttpStatus.OK);
    }

    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @GetMapping(value = "/productBrand/desc/{description}")
    public ResponseEntity<List<ProductBrand>> getByDescriptionProductBrand(@PathVariable String description){

        List<ProductBrand> roleAccessList = productBrandService.getByDescription(description.toUpperCase());

        return new ResponseEntity<>(roleAccessList, HttpStatus.OK);
    }
}
