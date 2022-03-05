package ca.com.rlsp.ecommerce.repository;

import ca.com.rlsp.ecommerce.model.LegalPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface LegalPersonRepository extends JpaRepository<LegalPerson, Long> {

}
