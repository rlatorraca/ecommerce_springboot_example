package ca.com.rlsp.ecommerce.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "purchase_invoice")
@SequenceGenerator(name = "seq_purchase_invoice", sequenceName = "seq_purchase_invoice", initialValue = 1 , allocationSize = 1)
public class PurchaseInvoice implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_purchase_invoice")
    private Long id;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "invoice_serie")
    private String invoiceSerie;

    @Column(name = "description")
    private String description;

    @Column(name = "total_value")
    private BigDecimal totalValue;

    @Column(name = "discount_value")
    private BigDecimal discountValue;

    @Column(name = "provincial_taxes")
    private BigDecimal provincial_taxes;

    @Column(name = "federal_taxes")
    private BigDecimal federal_taxes;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_sale")
    private Date dateSale;

    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "person_id",
                nullable = false,
                foreignKey = @ForeignKey(
                        value = ConstraintMode.CONSTRAINT,
                        name = "person_fk"))
    private Person person;

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

        PurchaseInvoice that = (PurchaseInvoice) o;

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

    public String getInvoiceSerie() {
        return invoiceSerie;
    }

    public void setInvoiceSerie(String invoiceSerie) {
        this.invoiceSerie = invoiceSerie;
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

    public BigDecimal getProvincial_taxes() {
        return provincial_taxes;
    }

    public void setProvincial_taxes(BigDecimal provincial_taxes) {
        this.provincial_taxes = provincial_taxes;
    }

    public BigDecimal getFederal_taxes() {
        return federal_taxes;
    }

    public void setFederal_taxes(BigDecimal federal_taxes) {
        this.federal_taxes = federal_taxes;
    }

    public Date getDateSale() {
        return dateSale;
    }

    public void setDateSale(Date dateSale) {
        this.dateSale = dateSale;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public TradePayable getTradePayable() {
        return tradePayable;
    }

    public void setTradePayable(TradePayable tradePayable) {
        this.tradePayable = tradePayable;
    }
}
