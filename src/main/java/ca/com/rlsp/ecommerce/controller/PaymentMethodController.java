package ca.com.rlsp.ecommerce.controller;

import ca.com.rlsp.ecommerce.exception.EcommerceException;
import ca.com.rlsp.ecommerce.model.PaymentMethod;
import ca.com.rlsp.ecommerce.service.PaymentMethodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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


    @ResponseBody
    @GetMapping(value = "/listPaymentMethodByCompany/{companyId}")
    public ResponseEntity<List<PaymentMethod>> listPaymentMethodByCompany(@PathVariable(value = "companyId") Long ecommerceid){

        return new ResponseEntity<List<PaymentMethod>>(paymentMethodService.findAllPaymentsMethodByCompanyId(ecommerceid), HttpStatus.OK);

    }


    @ResponseBody
    @GetMapping(value = "/listAllPaymentMethod")
    public ResponseEntity<List<PaymentMethod>> listAllPaymentMethod(){

        return new ResponseEntity<List<PaymentMethod>>(paymentMethodService.findAllPaymentsMethods(), HttpStatus.OK);

    }

}
