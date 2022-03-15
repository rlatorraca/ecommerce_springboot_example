package ca.com.rlsp.ecommerce.repository;

import ca.com.rlsp.ecommerce.model.LegalPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PersonRepository extends JpaRepository<LegalPerson, Long> {

    @Query(value = "select lp from  legal_person  lp where lp.businessNumber = ?1")
    public LegalPerson existBusinessNumberRegistered(String businessNumber);
}
