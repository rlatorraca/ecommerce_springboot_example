package ca.com.rlsp.ecommerce.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "discount_coupon")
@SequenceGenerator(name = "seq_discount_coupon", sequenceName = "seq_discount_coupon", initialValue = 1 , allocationSize = 1)
public class DiscountCoupon implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_discount_coupon")
    private Long id;

    @Column(name = "coupon_description", nullable = false)
    private String couponDescription;

    private BigDecimal realValueDiscount;

    private BigDecimal percentualValueDiscount;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date couponValidate;

    /* COMPANY | EMPRESA */
    @ManyToOne(targetEntity = LegalPerson.class)
    @JoinColumn(name = "ecommerce_company_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "ecommerce_company_fk"))
    private LegalPerson ecommerceCompany;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscountCoupon that = (DiscountCoupon) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCouponDescription() {
        return couponDescription;
    }

    public void setCouponDescription(String couponDescription) {
        this.couponDescription = couponDescription;
    }

    public BigDecimal getRealValueDiscount() {
        return realValueDiscount;
    }

    public void setRealValueDiscount(BigDecimal realValueDiscount) {
        this.realValueDiscount = realValueDiscount;
    }

    public BigDecimal getPercentualValueDiscount() {
        return percentualValueDiscount;
    }

    public LegalPerson getEcommerceCompany() {
        return ecommerceCompany;
    }

    public void setEcommerceCompany(LegalPerson ecommerceCompany) {
        this.ecommerceCompany = ecommerceCompany;
    }

    public void setPercentualValueDiscount(BigDecimal percentualValueDiscount) {
        this.percentualValueDiscount = percentualValueDiscount;
    }

    public Date getCouponValidate() {
        return couponValidate;
    }

    public void setCouponValidate(Date couponValidate) {
        this.couponValidate = couponValidate;
    }
}
