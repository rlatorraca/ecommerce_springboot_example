package ca.com.rlsp.ecommerce;

import ca.com.rlsp.ecommerce.util.BusinessNumberValidator;
import ca.com.rlsp.ecommerce.util.SinNumberValidator;

public class TestBusinessNumberSinNumberValidator {

    public static void main(String[] args) {
        boolean isBusinessNumber = BusinessNumberValidator.isCNPJ("89.480.195/0001-28");
        System.out.println("isBusinessNumber: "  + isBusinessNumber);


        boolean isSinNumber = SinNumberValidator.isCPF("497.657.381-88");
        System.out.println("isSinNumber: "  + isSinNumber);
    }
}
