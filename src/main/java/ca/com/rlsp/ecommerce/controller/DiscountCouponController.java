package ca.com.rlsp.ecommerce.controller;


import ca.com.rlsp.ecommerce.exception.EcommerceException;
import ca.com.rlsp.ecommerce.model.DiscountCoupon;
import ca.com.rlsp.ecommerce.model.Product;
import ca.com.rlsp.ecommerce.service.DiscountCouponService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class DiscountCouponController {

    private DiscountCouponService discountCouponService;

    public DiscountCouponController(DiscountCouponService discountCouponService) {
        this.discountCouponService = discountCouponService;
    }

    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @PostMapping(path = "/saveDiscountCoupom")
    public ResponseEntity<DiscountCoupon> saveDiscountCoupom(@RequestBody @Valid DiscountCoupon discountCoupon)  {

        DiscountCoupon discountCouponSaved = discountCouponService.saveAndFlush(discountCoupon);

        return new ResponseEntity<DiscountCoupon>(discountCouponSaved, HttpStatus.CREATED);
    }

    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @DeleteMapping(path = "/deleteDiscountCoupom")
    public ResponseEntity<DiscountCoupon> deleteDiscountCoupom(@RequestBody @Valid DiscountCoupon discountCoupon)  {

        discountCouponService.deleteById(discountCoupon.getId());
        return new ResponseEntity("Deleted Discount Cuoupon By Object", HttpStatus.OK);
    }

    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @DeleteMapping(path = "/deleteDiscountCoupom/{id}")
    public ResponseEntity<DiscountCoupon> deleteDiscountCoupom(@RequestBody @Valid Long id)  {

        discountCouponService.deleteById(id);
        return new ResponseEntity("Deleted Discount Cuoupon By Id : " + id, HttpStatus.OK);
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

    @ResponseBody
    @GetMapping(value = "/getOneDiscountCoupons/{id}")
    public ResponseEntity<DiscountCoupon> getOneDiscountCoupons(@RequestBody @Valid Long id){

        return new ResponseEntity<DiscountCoupon>(discountCouponService.getOneDiscoutnCoupon(id) , HttpStatus.OK);
    }
}
