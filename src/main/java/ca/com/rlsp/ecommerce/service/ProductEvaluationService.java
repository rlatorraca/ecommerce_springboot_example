package ca.com.rlsp.ecommerce.service;

import ca.com.rlsp.ecommerce.model.ProductEvaluation;
import ca.com.rlsp.ecommerce.model.ProductImage;
import ca.com.rlsp.ecommerce.repository.ProductEvaluationRepository;
import ca.com.rlsp.ecommerce.repository.ProductImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductEvaluationService {

    private ProductEvaluationRepository productEvaluationRepository;

    public ProductEvaluationService(ProductEvaluationRepository productEvaluationRepository) {
        this.productEvaluationRepository = productEvaluationRepository;
    }

    public ProductEvaluation save(ProductEvaluation productEvaluation) {

        return productEvaluationRepository.save(productEvaluation);
    }

    public ProductEvaluation saveAndFlush(ProductEvaluation productEvaluation) {

        return productEvaluationRepository.saveAndFlush(productEvaluation);
    }


    public void deleteEcommerceEvaluationByEcommerceId(Long ecommerceId) {
        productEvaluationRepository.deleteById(ecommerceId);
    }


    public List<ProductEvaluation> fetchProductEvaluation(Long productId){

        return productEvaluationRepository.fetchProductEvaluation(productId);
    }


    public List<ProductEvaluation> getProductEvaluationByNaturalPerson(Long naturalPersonId) {
        return productEvaluationRepository.fetchProductEvaluationByNaturalPerson(naturalPersonId);
    }

    public List<ProductEvaluation> getProductEvaluationByIdAndByPerson(Long productId, Long personId) {
        return productEvaluationRepository.fetchProductEvaluationAndNaturalPerson(productId, personId);
    }
}
