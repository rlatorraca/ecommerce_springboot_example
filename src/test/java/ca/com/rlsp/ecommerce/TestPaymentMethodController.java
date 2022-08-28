package ca.com.rlsp.ecommerce;

import ca.com.rlsp.ecommerce.controller.PaymentMethodController;

import junit.framework.TestCase;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

@Profile("ut")
@SpringBootTest(classes = EcommerceSpringbootExampleApplication.class)
public class TestPaymentMethodController extends TestCase {

    @Autowired
    private PaymentMethodController paymentMethodController;


    @Test
    public void istAllPaymentMethod(){
        paymentMethodController.listAllPaymentMethod();
    }

    @Test
    public void listPaymentMethodByCompany(){
        paymentMethodController.listPaymentMethodByCompany(13L);
    }


}
