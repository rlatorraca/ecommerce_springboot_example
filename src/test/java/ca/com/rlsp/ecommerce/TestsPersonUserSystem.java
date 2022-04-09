package ca.com.rlsp.ecommerce;

import ca.com.rlsp.ecommerce.controller.PersonController;
import ca.com.rlsp.ecommerce.enums.AddressType;
import ca.com.rlsp.ecommerce.enums.PersonType;
import ca.com.rlsp.ecommerce.exception.EcommerceException;
import ca.com.rlsp.ecommerce.model.Address;
import ca.com.rlsp.ecommerce.model.LegalPerson;
import ca.com.rlsp.ecommerce.model.NaturalPerson;
import ca.com.rlsp.ecommerce.repository.LegalPersonRepository;
import ca.com.rlsp.ecommerce.util.BusinessNumberValidator;
import ca.com.rlsp.ecommerce.util.SinNumberValidator;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import javax.mail.MessagingException;
import java.util.Calendar;


@Profile("ut")
@SpringBootTest(classes = EcommerceSpringbootExampleApplication.class)
class TestsPersonUserSystem extends TestCase {


    @Autowired
    private PersonController personController;

    @Autowired
    private LegalPersonRepository legalPersonRepository;

    @Test
    public void testRestRegisterLegalUserSystem() throws EcommerceException, MessagingException {

        LegalPerson legalPerson = new LegalPerson();
        Address address01 = new Address();
        Address address02 = new Address();
        LegalPerson person = new LegalPerson();


        legalPerson.setName("Staples Simples");
        legalPerson.setComercialName("Staples Simples Ltda");
        legalPerson.setLegalName("Simple Test");
        legalPerson.setEmail("email"+ Calendar.getInstance().getTimeInMillis()+"@simpletest.com.ca");

        String legalNumber = "38.355.481/0001-20";
        Boolean isBusinessNumber = BusinessNumberValidator.isCNPJ(legalNumber);
        if(isBusinessNumber) {
            legalPerson.setBusinessNumber(removeSpecialCharacters(legalNumber));
        }
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

    @Test
    public void testRestRegisterNormalUserSystem() throws EcommerceException, MessagingException {

        LegalPerson legalPerson =  legalPersonRepository.existBusinessNumberRegistered("1648315302501");

        NaturalPerson naturalPerson = new NaturalPerson();
        Address address01 = new Address();
        Address address02 = new Address();
        LegalPerson person = new LegalPerson();


        naturalPerson.setName("Staples Simples");

        naturalPerson.setName("Test Name");

        String naturalNumber = "241.303.982-10";
        Boolean isBusinessNumber = SinNumberValidator.isCPF(naturalNumber);
        if(isBusinessNumber) {
            naturalPerson.setSinNumber(removeSpecialCharacters(naturalNumber));
        }
        naturalPerson.setEmail("email"+ Calendar.getInstance().getTimeInMillis()+"@simpletest.com.ca");
        naturalPerson.setTelephone("(647) 333-44444");
        naturalPerson.setPersonType(PersonType.USER);
        naturalPerson.setEcommerceCompany(legalPerson);



        address01.setPerson(naturalPerson);
        address01.setAddressLine01("Street 0111");
        address01.setAddressType(AddressType.DELIVERY);
        address01.setAddressLine02("corner");
        address01.setCity("Montreal");
        address01.setCountry("Canada");
        address01.setNeighborhood("downtown");
        address01.setNumber("123123");
        address01.setProvince("QC");
        address01.setZipPostalCode("E1D2R4");


        address02.setPerson(naturalPerson);
        address02.setAddressLine01("Street 33333");
        address02.setAddressType(AddressType.BILLING);
        address02.setAddressLine02("corner");
        address02.setCity("Ville de Quebec");
        address02.setCountry("Canada");
        address02.setNeighborhood("Montagne");
        address02.setNumber("123123");
        address02.setProvince("QC");
        address02.setZipPostalCode("T6Y4Y3");


        naturalPerson.getAddresses().add(address01);
        naturalPerson.getAddresses().add(address02);


        naturalPerson = personController.saveNaturalPerson(naturalPerson).getBody();

        assertEquals(true, naturalPerson.getId() > 0);

        for(Address address : naturalPerson.getAddresses()) {
            assertEquals(true, address.getId() > 0);
        }

        assertEquals(2, naturalPerson.getAddresses().size());
    }


    private String removeSpecialCharacters(String number) {
        return  number.replaceAll("\\.", "")
                .replaceAll("\\/", "")
                .replaceAll("\\-", "");
    }
}
