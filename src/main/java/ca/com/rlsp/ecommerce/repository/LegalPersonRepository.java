package ca.com.rlsp.ecommerce.repository;

import ca.com.rlsp.ecommerce.model.LegalPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface LegalPersonRepository extends JpaRepository<LegalPerson, Long> {

    /* LEGAL PERSON */
    @Query(value = "SELECT lp FROM  legal_person lp WHERE lp.businessNumber = ?1")
    public List<LegalPerson> queryByBusinessNumberRegistered(String businessNumber);

    @Query(value = "SELECT lp FROM  legal_person lp WHERE lp.provinceRegistration = ?1")
    public List<LegalPerson> queryByProvincialRegistration(String provinceRegistration);

    @Query(value = "SELECT lp FROM  legal_person lp WHERE lp.businessNumber = ?1")
    public List<LegalPerson> queryByBusinessNumberRegisteredlist(String businessNumber);

    @Query(value = "SELECT lp FROM  legal_person lp WHERE lp.provinceRegistration = ?1")
    public  List<LegalPerson> queryByProvincialRegistrationList(String provinceRegistration);

    @Query(value = "SELECT lp FROM  legal_person lp WHERE upper(trim(lp.name)) like %?1% ")
    public  List<LegalPerson> queryLegalPersonByName(String name);





}
