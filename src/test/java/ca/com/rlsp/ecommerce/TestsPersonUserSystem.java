package ca.com.rlsp.ecommerce;

import ca.com.rlsp.ecommerce.controller.PersonController;
import ca.com.rlsp.ecommerce.enums.AddressType;
import ca.com.rlsp.ecommerce.exception.EcommerceException;
import ca.com.rlsp.ecommerce.model.Address;
import ca.com.rlsp.ecommerce.model.LegalPerson;
import ca.com.rlsp.ecommerce.model.Person;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;


@Profile("ut")
@SpringBootTest(classes = EcommerceSpringbootExampleApplication.class)
public class TestsPersonUserSystem extends TestCase {


    @Autowired
    private PersonController personController;

    @Test
    public void testRestRegisterUserSystem() throws EcommerceException {

        LegalPerson legalPerson = new LegalPerson();
        Address address = new Address();
        Person person = new LegalPerson();

        address.setAddressLine01("1221 Chemin Blue");
        address.setAddressType(AddressType.BILLING);
        address.setNeighborhood("Downtown");
        address.setCity("Ottawa");
        address.setCountry("Canada");
        address.setNumber("32323");
        address.setProvince("ON");
        address.setPerson(person);

        legalPerson.setName("Staples Simples");
        legalPerson.setComercialName("Staples Simples Ltda");
        //legalPerson.setAddresses(List.of(address));
        legalPerson.setLegalName("Simple Test");
        legalPerson.setEmail("email@simpletest.com.ca");
        legalPerson.setBusinessNumber("322.2323/0001-09");
        legalPerson.setProvinceRegistration("13123212");
        legalPerson.setTelephone("(647) 333-44444");

        personController.saveLegalPerson(legalPerson);
    }
}
