package ca.com.rlsp.ecommerce.service;


import ca.com.rlsp.ecommerce.model.DiscountCoupon;
import ca.com.rlsp.ecommerce.repository.DiscountCupomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountCouponService {

    private DiscountCupomRepository discountCupomRepository;

    public DiscountCouponService(DiscountCupomRepository discountCupomRepository) {
        this.discountCupomRepository = discountCupomRepository;
    }


    public List<DiscountCoupon> discountCouponByEcommerce(Long ecommerceId) {
        return discountCupomRepository.discountCouponByEcommerce(ecommerceId);
    }

    public List<DiscountCoupon> findAllDiscountCoupon() {
        return discountCupomRepository.findAll();
    }

    public DiscountCoupon saveAndFlush(DiscountCoupon discountCoupon) {

        return discountCupomRepository.saveAndFlush(discountCoupon);
    }

    public void deleteById(Long id) {

        discountCupomRepository.deleteById(id);
    }

    public DiscountCoupon getOneDiscoutnCoupon(Long id) {
        return discountCupomRepository.findById(id).get();
    }
}
