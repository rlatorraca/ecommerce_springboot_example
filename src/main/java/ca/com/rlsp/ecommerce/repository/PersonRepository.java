package ca.com.rlsp.ecommerce.repository;

import ca.com.rlsp.ecommerce.model.LegalPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PersonRepository extends JpaRepository<LegalPerson, Long> {

    /* LEGAL PERSON */
    @Query(value = "SELECT lp FROM  legal_person lp WHERE lp.businessNumber = ?1")
    public LegalPerson existBusinessNumberRegistered(String businessNumber);

    @Query(value = "SELECT lp FROM  legal_person lp WHERE lp.provinceRegistration = ?1")
    public LegalPerson existProvincialRegistration(String provinceRegistration);


    /* NATURAL PERSON */
    @Query(value = "SELECT np FROM  natural_person np WHERE np.sinNumber = ?1")
    public LegalPerson existSinNumberRegistered(String businessNumber);


}
