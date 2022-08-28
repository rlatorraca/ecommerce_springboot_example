package ca.com.rlsp.ecommerce;


import ca.com.rlsp.ecommerce.controller.DiscountCouponController;
import junit.framework.TestCase;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

@Profile("ut")
@SpringBootTest(classes = EcommerceSpringbootExampleApplication.class)
public class TestDiscountCouponController extends TestCase {

    @Autowired
    private DiscountCouponController discountCouponController;


    @Test
    public void listAllDiscountCoupons() {
        discountCouponController.listAllDiscountCoupons();
    }

    @Test
    public void listAllDiscountCouponByEcommerce() {
        discountCouponController.listAllDiscountCouponByEcommerce(1L);
    }
}
