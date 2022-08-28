package ca.com.rlsp.ecommerce.controller;


import ca.com.rlsp.ecommerce.model.DiscountCoupon;
import ca.com.rlsp.ecommerce.service.DiscountCouponService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DiscountCouponController {

    private DiscountCouponService discountCouponService;

    public DiscountCouponController(DiscountCouponService discountCouponService) {
        this.discountCouponService = discountCouponService;
    }

    @ResponseBody
    @GetMapping(value = "/listAllDiscountCouponByEcommerce/{ecommerceId}")
    public ResponseEntity<List<DiscountCoupon>> listAllDiscountCouponByEcommerce(@PathVariable("ecommerceId") Long ecommerceId){

        return new ResponseEntity<List<DiscountCoupon>>(discountCouponService.discountCouponByEcommerce(ecommerceId), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/listAllDiscountCoupons")
    public ResponseEntity<List<DiscountCoupon>> listAllDiscountCoupons(){

        return new ResponseEntity<List<DiscountCoupon>>(discountCouponService.findAllDiscountCoupon() , HttpStatus.OK);
    }
}
