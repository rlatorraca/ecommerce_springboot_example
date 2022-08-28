package ca.com.rlsp.ecommerce.service;

import ca.com.rlsp.ecommerce.model.PaymentMethod;
import ca.com.rlsp.ecommerce.model.Product;
import ca.com.rlsp.ecommerce.repository.PaymentMethodRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PaymentMethodService {


    private PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodService(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    public PaymentMethod save(PaymentMethod paymentMethod) {

        return paymentMethodRepository.save(paymentMethod);
    }

    public List<PaymentMethod> findAllPaymentsMethodByCompanyId(Long ecommerceid) {
        return paymentMethodRepository.findAllPaymentsMethodByCompanyId(ecommerceid);
    }

    public List<PaymentMethod> findAllPaymentsMethods() {
        return paymentMethodRepository.findAll();
    }
}
