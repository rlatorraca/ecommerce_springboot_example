package ca.com.rlsp.ecommerce.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "product_sales_ecommerce")
@SequenceGenerator(name = "seq_product_sales_ecommerce", sequenceName = "seq_product_sales_ecommerce", initialValue = 1 , allocationSize = 1)
public class ProductSalesEcommerce implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_product_sales_ecommerce")
    private Long id;

    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "person_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "person_fk"))
    private Person person;

    /* COMPANY | EMPRESA */
    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "ecommerce_company_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "ecommerce_company_fk"))
    private Person ecommerceCompany;

    @ManyToOne
    @JoinColumn(name = "billing_address_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "billing_address_fk"))
    private Address billingAddress ;

    @ManyToOne
    @JoinColumn(name = "shipping_address_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "shipping_address_fk"))
    private Address shippingAddress ;

    @Column(name = "total_value" , nullable = false)
    private BigDecimal totalValue;

    @Column(name = "total_discount")
    private BigDecimal totalDiscount;

    @Column(name = "delivery_value", nullable = false)
    private BigDecimal deliveryValue;

    @Column(name = "days_to_delivery", nullable = false)
    private Integer daysToDelivery;

    @Column(name = "sale_date", nullable = false)
    private Date saleDate;

    @Column(name = "delivery_date", nullable = false)
    private Date deliveryDate;

    @ManyToOne
    @JoinColumn(name = "payment_method_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "payment_method_fk"))
    private PaymentMethod paymentMethod;

    @OneToOne
    @JoinColumn(name = "sales_invoice_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "sales_invoice_fk"))
    private SalesInvoice salesInvoice;

    @ManyToOne
    @JoinColumn(name = "discount_coupon_id",
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "discount_coupon_fk"))
    private DiscountCoupon discountCoupon;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public BigDecimal getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(BigDecimal totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public SalesInvoice getSalesInvoice() {
        return salesInvoice;
    }

    public void setSalesInvoice(SalesInvoice salesInvoice) {
        this.salesInvoice = salesInvoice;
    }

    public BigDecimal getDeliveryValue() {
        return deliveryValue;
    }

    public void setDeliveryValue(BigDecimal deliveryValue) {
        this.deliveryValue = deliveryValue;
    }

    public Integer getDaysToDelivery() {
        return daysToDelivery;
    }

    public void setDaysToDelivery(Integer daysToDelivery) {
        this.daysToDelivery = daysToDelivery;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public DiscountCoupon getDiscountCoupon() {
        return discountCoupon;
    }

    public void setDiscountCoupon(DiscountCoupon discountCoupon) {
        this.discountCoupon = discountCoupon;
    }

    public Person getEcommerceCompany() {
        return ecommerceCompany;
    }

    public void setEcommerceCompany(Person ecommerceCompany) {
        this.ecommerceCompany = ecommerceCompany;
    }
}
