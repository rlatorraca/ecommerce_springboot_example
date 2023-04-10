package ca.com.rlsp.ecommerce.model;

import ca.com.rlsp.ecommerce.enums.StatusProductSalesEcommerce;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "product_sales_ecommerce")
@SequenceGenerator(name = "seq_product_sales_ecommerce", sequenceName = "seq_product_sales_ecommerce", initialValue = 1 , allocationSize = 1)
public class ProductSalesEcommerce implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_product_sales_ecommerce")
    private Long id;

    @NotNull(message = "Buyer must be informed")
    @ManyToOne(targetEntity = NaturalPerson.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "person_fk"))
    private NaturalPerson person;

    /* COMPANY | EMPRESA */
    @NotNull(message = "Ecommerce owner of the sale must be informed")
    @ManyToOne(targetEntity = LegalPerson.class)
    @JoinColumn(name = "ecommerce_company_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "ecommerce_company_fk"))
    private LegalPerson ecommerceCompany;


    @NotNull(message = "Billing address must be informed")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "billing_address_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "billing_address_fk"))
    private Address billingAddress ;

    @NotNull(message = "Shipping address must be informed")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipping_address_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "shipping_address_fk"))
    private Address shippingAddress ;

    @NotNull(message = "Total value must be informed")
    @Column(name = "total_value" , nullable = false)
    private BigDecimal totalValue;

    @Column(name = "total_discount")
    private BigDecimal totalDiscount;

    @Min(value = 5, message = "Delivery value is invalid")
    @NotNull(message = "Delivery value must be informed")
    @Column(name = "delivery_value", nullable = false)
    private BigDecimal deliveryValue;

    @Min(value = 1, message = "Days to delivery is invalid")
    @Column(name = "days_to_delivery", nullable = false)
    private Integer daysToDelivery;

    @NotNull(message = "Sales date must be informed")
    @Column(name = "sale_date", nullable = false)
    private Date saleDate;

    @NotNull(message = "Delivery date must be informed")
    @Column(name = "delivery_date", nullable = false)
    private Date deliveryDate;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Sales status must be entered")
    @Column(name = "status_sales_Ecommerce", nullable = false)
    private StatusProductSalesEcommerce statusProductSalesEcommerce;

    @NotNull(message = "Payment method must be informed")
    @ManyToOne
    @JoinColumn(name = "payment_method_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "payment_method_fk"))
    private PaymentMethod paymentMethod;

    @JsonIgnoreProperties(allowGetters = true)
    @NotNull(message = "Sales invoice must be informad")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sales_invoice_id",
            nullable = true,
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

    @OneToMany(mappedBy = "productSalesEcommerce", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ItemSaleEcommerce> itemsSaleEcommerce = new ArrayList<ItemSaleEcommerce>();

    private Boolean deleted = Boolean.FALSE;

    private String tagShippingCode;

    private String urlPrintingTag;

    /* Shipping Company chosen */
    private String shippingCompany;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NaturalPerson getPerson() {
        return person;
    }

    public void setPerson(NaturalPerson person) {
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

    public LegalPerson getEcommerceCompany() {
        return ecommerceCompany;
    }

    public void setEcommerceCompany(LegalPerson ecommerceCompany) {
        this.ecommerceCompany = ecommerceCompany;
    }

    public List<ItemSaleEcommerce> getItemsSaleEcommerce() {
        return itemsSaleEcommerce;
    }

    public void setItemsSaleEcommerce(List<ItemSaleEcommerce> itemsSaleEcommerce) {
        this.itemsSaleEcommerce = itemsSaleEcommerce;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public StatusProductSalesEcommerce getStatusProductSalesEcommerce() {
        return statusProductSalesEcommerce;
    }

    public void setStatusProductSalesEcommerce(StatusProductSalesEcommerce statusProductSalesEcommerce) {
        this.statusProductSalesEcommerce = statusProductSalesEcommerce;
    }

    public String getTagShippingCode() {
        return tagShippingCode;
    }

    public void setTagShippingCode(String tagShippingCode) {
        this.tagShippingCode = tagShippingCode;
    }

    public String getUrlPrintingTag() {
        return urlPrintingTag;
    }

    public void setUrlPrintingTag(String urlPrintingTag) {
        this.urlPrintingTag = urlPrintingTag;
    }

    public String getShippingCompany() {
        return shippingCompany;
    }

    public void setShippingCompany(String shippingCompany) {
        this.shippingCompany = shippingCompany;
    }
}
