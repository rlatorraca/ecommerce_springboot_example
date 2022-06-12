package ca.com.rlsp.ecommerce.repository;

import ca.com.rlsp.ecommerce.model.LegalPerson;
import ca.com.rlsp.ecommerce.model.NaturalPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface NaturalPersonRepository extends JpaRepository<NaturalPerson, Long> {

    /* NATURAL PERSON */
    @Query(value = "SELECT np FROM  natural_person np WHERE np.sinNumber = ?1")
    public List<NaturalPerson> queryNaturalPersonBySinNumber(String sinNumber);

    @Query(value = "SELECT np FROM  natural_person np WHERE upper(trim(np.name)) like %?1% ")
    public List<NaturalPerson> queryNaturalPersonByName(String name);


}
