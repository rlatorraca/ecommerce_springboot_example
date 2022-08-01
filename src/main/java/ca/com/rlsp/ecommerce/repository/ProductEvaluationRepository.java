package ca.com.rlsp.ecommerce.repository;

import ca.com.rlsp.ecommerce.model.ProductEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ProductEvaluationRepository extends JpaRepository<ProductEvaluation, Long> {

    @Query(value = "SELECT pe FROM ProductEvaluation pe WHERE pe.product.id = ?1")
    List<ProductEvaluation> fetchProductEvaluation(Long productId);


    @Query(value = "SELECT pe FROM ProductEvaluation pe WHERE pe.product.id = ?1 AND pe.person.id = ?2")
    List<ProductEvaluation> fetchProductEvaluationAndNaturalPerson(Long productId, Long personId);


    @Query(value = "SELECT pe FROM ProductEvaluation pe WHERE pe.person.id = ?1")
    List<ProductEvaluation> fetchProductEvaluationByNaturalPerson(Long naturalPersonId);

}
