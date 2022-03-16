package ca.com.rlsp.ecommerce;

import ca.com.rlsp.ecommerce.controller.PersonController;
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
        Address address = new Address();
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


        personController.saveLegalPerson(legalPerson);
    }
}
