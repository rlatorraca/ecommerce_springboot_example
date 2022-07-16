package ca.com.rlsp.ecommerce.model;

import ca.com.rlsp.ecommerce.enums.StatusPayable;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "trade_payable")
@SequenceGenerator(name = "seq_trade_payable", sequenceName = "seq_trade_payable", initialValue = 1 , allocationSize = 1)
public class TradePayable implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_trade_payable")
    private Long id;

    @NotEmpty(message = "Trade payable description must be informed, not empty")
    @Column(nullable = false, name="description")
    private String description;


    @Column(nullable = false,name = "status_debtor")
    @Enumerated(EnumType.STRING)
    private StatusPayable statusDebtor;


    @Column(nullable = false, name = "due_date")
    @Temporal(TemporalType.DATE)
    private Date dueDate;

    @Column(name = "payment_date")
    @Temporal(TemporalType.DATE)
    private Date paymentDate;

    @Column(nullable = false, name = "total_value")
    private BigDecimal totalValue;

    @Column(name = "total_discount")
    private BigDecimal totalDiscount;

    @ManyToOne(targetEntity = NaturalPerson.class)
    @JoinColumn(name = "person_id",
                nullable = false,
                foreignKey = @ForeignKey(
                        value = ConstraintMode.CONSTRAINT,
                        name = "person_fk"))
    private NaturalPerson person;

    @ManyToOne(targetEntity = LegalPerson.class)
    @JoinColumn(name = "person_provider_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "person_provider_fk"))
    private LegalPerson person_provider;

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
        TradePayable that = (TradePayable) o;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StatusPayable getStatusDebtor() {
        return statusDebtor;
    }

    public void setStatusDebtor(StatusPayable statusDebtor) {
        this.statusDebtor = statusDebtor;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
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

    public NaturalPerson getPerson() {
        return person;
    }

    public void setPerson(NaturalPerson person) {
        this.person = person;
    }

    public LegalPerson getPerson_provider() {
        return person_provider;
    }

    public void setPerson_provider(LegalPerson person_provider) {
        this.person_provider = person_provider;
    }

    public LegalPerson getEcommerceCompany() {
        return ecommerceCompany;
    }

    public void setEcommerceCompany(LegalPerson ecommerceCompany) {
        this.ecommerceCompany = ecommerceCompany;
    }
}
