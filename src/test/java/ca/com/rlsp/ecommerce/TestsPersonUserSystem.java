package ca.com.rlsp.ecommerce;

import ca.com.rlsp.ecommerce.enums.AddressType;
import ca.com.rlsp.ecommerce.model.Address;
import ca.com.rlsp.ecommerce.model.LegalPerson;
import ca.com.rlsp.ecommerce.model.NaturalPerson;
import ca.com.rlsp.ecommerce.model.Person;
import ca.com.rlsp.ecommerce.repository.LegalPersonRepository;
import ca.com.rlsp.ecommerce.repository.NaturalPersonRepository;
import ca.com.rlsp.ecommerce.service.PersonUserSystemService;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Profile("ut")
@SpringBootTest(classes = EcommerceSpringbootExampleApplication.class)
public class TestsPersonUserSystem  extends TestCase {

    @Autowired
    private PersonUserSystemService personUserSystemService;

    @Autowired
    private NaturalPersonRepository naturalPersonRepository;

    @Autowired
    private LegalPersonRepository legalPersonRepository;

    @Test
    public void testRestRegisterUserSystem() {

        LegalPerson legalPerson = new LegalPerson();
        Address address = new Address();

        address.setAddressLine01("1221 Chemin Blue");
        address.setAddressType(AddressType.BILLING);
        address.setNeighborhood("Downtown");
        address.setCity("Ottawa");
        address.setCountry("Canada");
        address.setNumber("32323");
        address.setProvince("ON");
        address.setPerson();

        legalPerson.setName("Staples Simples");
        legalPerson.setComercialName("Staples Simples Ltda");
        legalPerson.setAddresses(List.of(address));
        legalPerson.setLegalName("Simple Test");
        legalPerson.setEmail("email@simpletest.com.ca");
        legalPerson.setBusinessNumber("322.2323/0001-09");
        legalPerson.setProvinceRegistration("13123212");
        legalPerson.setTelephone("(647) 333-44444");

        legalPersonRepository.save(legalPerson);
    }
}
