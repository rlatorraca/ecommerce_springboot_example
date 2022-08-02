package ca.com.rlsp.ecommerce.controller;

import ca.com.rlsp.ecommerce.exception.EcommerceException;
import ca.com.rlsp.ecommerce.model.PaymentMethod;
import ca.com.rlsp.ecommerce.service.PaymentMethodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class PaymentMethodController {


    private PaymentMethodService paymentMethodService;

    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    @ResponseBody
    @PostMapping(value = "/savePaymentMethod")
    public ResponseEntity<PaymentMethod> savePaymentMethod(@RequestBody @Valid PaymentMethod paymentMethod)
            throws EcommerceException {

        paymentMethod = paymentMethodService.save(paymentMethod);

        return new ResponseEntity<PaymentMethod>(paymentMethod, HttpStatus.CREATED);
    }
}
