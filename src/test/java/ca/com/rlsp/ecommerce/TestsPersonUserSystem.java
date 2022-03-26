package ca.com.rlsp.ecommerce;

import ca.com.rlsp.ecommerce.controller.PersonController;
import ca.com.rlsp.ecommerce.enums.AddressType;
import ca.com.rlsp.ecommerce.enums.PersonType;
import ca.com.rlsp.ecommerce.exception.EcommerceException;
import ca.com.rlsp.ecommerce.model.Address;
import ca.com.rlsp.ecommerce.model.LegalPerson;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.util.Calendar;


@Profile("ut")
@SpringBootTest(classes = EcommerceSpringbootExampleApplication.class)
class TestsPersonUserSystem extends TestCase {


    @Autowired
    private PersonController personController;

    @Test
    public void testRestRegisterUserSystem() throws EcommerceException {

        LegalPerson legalPerson = new LegalPerson();
        Address address01 = new Address();
        Address address02 = new Address();
        LegalPerson person = new LegalPerson();


        legalPerson.setName("Staples Simples");
        legalPerson.setComercialName("Staples Simples Ltda");
        legalPerson.setLegalName("Simple Test");
        legalPerson.setEmail("email"+ Calendar.getInstance().getTimeInMillis()+"@simpletest.com.ca");
        legalPerson.setBusinessNumber("" + Calendar.getInstance().getTimeInMillis());
        legalPerson.setProvinceRegistration("13123212");
        legalPerson.setTelephone("(647) 333-44444");
        legalPerson.setPersonType(PersonType.USER);
        legalPerson.setCityRegistration("Halifax");
        legalPerson.setCategory("XxX");


        address01.setPerson(legalPerson);
        address01.setAddressLine01("Street 0111");
        address01.setAddressType(AddressType.DELIVERY);
        address01.setAddressLine02("corner");
        address01.setCity("Montreal");
        address01.setCountry("Canada");
        address01.setNeighborhood("downtown");
        address01.setNumber("123123");
        address01.setProvince("QC");
        address01.setZipPostalCode("E1D2R4");


        address02.setPerson(legalPerson);
        address02.setAddressLine01("Street 33333");
        address02.setAddressType(AddressType.BILLING);
        address02.setAddressLine02("corner");
        address02.setCity("Ville de Quebec");
        address02.setCountry("Canada");
        address02.setNeighborhood("Montagne");
        address02.setNumber("123123");
        address02.setProvince("QC");
        address02.setZipPostalCode("T6Y4Y3");


        legalPerson.getAddresses().add(address01);
        legalPerson.getAddresses().add(address02);


        legalPerson = personController.saveLegalPerson(legalPerson).getBody();

        assertEquals(true, legalPerson.getId() > 0);

        for(Address address : legalPerson.getAddresses()) {
            assertEquals(true, address.getId() > 0);
        }

        assertEquals(2, legalPerson.getAddresses().size());
    }
}
