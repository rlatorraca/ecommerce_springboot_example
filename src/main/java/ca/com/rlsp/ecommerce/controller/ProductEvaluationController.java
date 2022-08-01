package ca.com.rlsp.ecommerce.controller;

import ca.com.rlsp.ecommerce.exception.EcommerceException;
import ca.com.rlsp.ecommerce.model.ProductEvaluation;

import ca.com.rlsp.ecommerce.service.ProductEvaluationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductEvaluationController {


    public static final String ECOMMERCE_NAME_MUST_BE_INFORMED_RLSP = "Ecommerce name must be informed. [RLSP]";
    public static final String EVALUATION_MUST_HAVE_A_PRODUCT_ASSOCIATED_RLSP = "Each evaluation must have a product associated. [RLSP]";
    public static final String EVALUATION_REMOVED_RLSP = "Evaluation removed. [RLSP]";
    private final ProductEvaluationService productEvaluationService;

    public ProductEvaluationController(ProductEvaluationService productEvaluationService) {
        this.productEvaluationService = productEvaluationService;
    }

    @ResponseBody
    @PostMapping(value = "/saveProductEvaluation")
    public ResponseEntity<ProductEvaluation> saveProductEvaluation(@RequestBody @Valid ProductEvaluation ProductEvaluation) throws EcommerceException {

        if (ProductEvaluation.getEcommerceCompany() == null || (ProductEvaluation.getEcommerceCompany() != null && ProductEvaluation.getEcommerceCompany().getId() <= 0)) {
            throw new EcommerceException(ECOMMERCE_NAME_MUST_BE_INFORMED_RLSP);
        }


        if(ProductEvaluation.getProduct() == null || (ProductEvaluation.getProduct() != null && ProductEvaluation.getProduct().getId() <= 0)) {
            throw new EcommerceException(EVALUATION_MUST_HAVE_A_PRODUCT_ASSOCIATED_RLSP);
        }


        if(ProductEvaluation.getEcommerceCompany() == null || (ProductEvaluation.getEcommerceCompany() != null && ProductEvaluation.getEcommerceCompany().getId() <= 0)) {
            throw new EcommerceException("Evaluation must have a legal person or a ecommerce associated. [RLSP]");
        }

        ProductEvaluation = productEvaluationService.saveAndFlush(ProductEvaluation);

        return new ResponseEntity<ProductEvaluation>(ProductEvaluation, HttpStatus.OK);

    }


    @ResponseBody
    @DeleteMapping(value = "/deleteEcommerceEvaluation/{evaluationId}")
    public ResponseEntity<?> deleteEcommerceEvaluation(@PathVariable("evaluationId") Long evaluationId) {

        productEvaluationService.deleteEcommerceEvaluationByEcommerceId(evaluationId);

        return new ResponseEntity<String>(EVALUATION_REMOVED_RLSP,HttpStatus.OK);
    }


    @ResponseBody
    @GetMapping(value = "/getProductEvaluations/{productId}")
    public ResponseEntity<List<ProductEvaluation>> getProductEvaluations(@PathVariable("productId") Long productId) {

        List<ProductEvaluation> productEvaluations = productEvaluationService.fetchProductEvaluation(productId);

        return new ResponseEntity<List<ProductEvaluation>>(productEvaluations,HttpStatus.OK);
    }


    @ResponseBody
    @GetMapping(value = "/getNaturalPersonEvaluations/{ecommerceId}")
    public ResponseEntity<List<ProductEvaluation>> getNaturalPersonEvaluations(@PathVariable("ecommerceId") Long ecommerceId) {

        List<ProductEvaluation> productEvaluations = productEvaluationService.getProductEvaluationByNaturalPerson(ecommerceId);

        return new ResponseEntity<List<ProductEvaluation>>(productEvaluations,HttpStatus.OK);
    }


    @ResponseBody
    @GetMapping(value = "/getProductEvaluationByIdAndByPerson/{productId}/{ecommerceId}")
    public ResponseEntity<List<ProductEvaluation>> getProductEvaluationByIdAndByPerson(@PathVariable("productId") Long productId, @PathVariable("ecommerceId") Long ecommerceId) {

        List<ProductEvaluation> productEvaluations = productEvaluationService.getProductEvaluationByIdAndByPerson(productId, ecommerceId);

        return new ResponseEntity<List<ProductEvaluation>>(productEvaluations,HttpStatus.OK);
    }
}
