package ca.com.rlsp.ecommerce.model;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "stock_purchase_invoice")
@SequenceGenerator(name = "seq_purchase_invoice", sequenceName = "seq_purchase_invoice", initialValue = 1 , allocationSize = 1)
public class StockPurchaseInvoice implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_purchase_invoice")
    private Long id;

    @NotEmpty(message = "Invoice number must be informed")
    @Column(name = "invoice_number", nullable = false)
    private String invoiceNumber;

    @NotEmpty(message = "Invoice series must be informed")
    @Column(name = "invoice_series", nullable = false)
    private String invoiceSeries;


    @Column(name = "description")
    private String description;

    @DecimalMin(value = "0.1", inclusive = false, message = "Total value must be informed")
    @Digits(integer=10, fraction=2)
    @Column(name = "total_value", nullable = false)
    private BigDecimal totalValue;


    @Column(name = "discount_value")
    private BigDecimal discountValue;

    @DecimalMin(value = "0.1", inclusive = false, message = "Provincial taxes value must be informed")
    @Digits(integer=10, fraction=2)
    @Column(name = "provincial_taxes", nullable = false)
    private BigDecimal provincialTaxes;


    @DecimalMin(value = "0.1", inclusive = false, message = "Federal taxes value must be informed")
    @Digits(integer=10, fraction=2)
    @Column(name = "federal_taxes", nullable = false)
    private BigDecimal federalTaxes;


    @Temporal(TemporalType.DATE)
    @Column(name = "date_sale", nullable = false)
    private Date dateSale;

    @ManyToOne(targetEntity = LegalPerson.class)
    @JoinColumn(name = "legal_person_vendor_id",
                nullable = false,
                foreignKey = @ForeignKey(
                        value = ConstraintMode.CONSTRAINT,
                        name = "person_fk"))
    private LegalPerson legalPersonVendor;

    /* COMPANY | EMPRESA */
    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "ecommerce_company_purchaser_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "ecommerce_company_fk"))
    private LegalPerson ecommerceCompanyPurchaser;

    @ManyToOne
    @JoinColumn(name = "trade_payable_id",
                nullable = false,
                foreignKey = @ForeignKey(
                        value = ConstraintMode.CONSTRAINT,
                        name = "trade_payable_fk"))
    private TradePayable tradePayable;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockPurchaseInvoice that = (StockPurchaseInvoice) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceSeries() {
        return invoiceSeries;
    }

    public void setInvoiceSeries(String invoiceSeries) {
        this.invoiceSeries = invoiceSeries;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public BigDecimal getProvincialTaxes() {
        return provincialTaxes;
    }

    public void setProvincialTaxes(BigDecimal provincialTaxes) {
        this.provincialTaxes = provincialTaxes;
    }

    public BigDecimal getFederalTaxes() {
        return federalTaxes;
    }

    public void setFederalTaxes(BigDecimal federalTaxes) {
        this.federalTaxes = federalTaxes;
    }

    public Date getDateSale() {
        return dateSale;
    }

    public void setDateSale(Date dateSale) {
        this.dateSale = dateSale;
    }

    public LegalPerson getLegalPersonVendor() {
        return legalPersonVendor;
    }

    public void setLegalPersonVendor(LegalPerson legalPersonVendor) {
        this.legalPersonVendor = legalPersonVendor;
    }

    public LegalPerson getEcommerceCompanyPurchaser() {
        return ecommerceCompanyPurchaser;
    }

    public void setEcommerceCompanyPurchaser(LegalPerson ecommerceCompanyPurchaser) {
        this.ecommerceCompanyPurchaser = ecommerceCompanyPurchaser;
    }

    public TradePayable getTradePayable() {
        return tradePayable;
    }

    public void setTradePayable(TradePayable tradePayable) {
        this.tradePayable = tradePayable;
    }
}
