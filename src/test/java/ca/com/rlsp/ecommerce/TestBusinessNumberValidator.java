package ca.com.rlsp.ecommerce;

import ca.com.rlsp.ecommerce.util.BusinessNumberValidator;

public class TestBusinessNumberValidator {

    public static void main(String[] args) {
        boolean isBusinessNumber = BusinessNumberValidator.isCNPJ("89.480.195/0001-28");
        System.out.println("isBusinessNumber: "  + isBusinessNumber);
    }
}
