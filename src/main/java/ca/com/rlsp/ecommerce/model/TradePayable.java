package ca.com.rlsp.ecommerce.model;

import ca.com.rlsp.ecommerce.enums.StatusPayable;

import javax.persistence.*;
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

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusPayable statusDebtor;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dueDate;

    @Temporal(TemporalType.DATE)
    private Date paymentDate;

    @Column(nullable = false)
    private BigDecimal totalValue;

    private BigDecimal totalDiscount;

    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "person_id",
                nullable = false,
                foreignKey = @ForeignKey(
                        value = ConstraintMode.CONSTRAINT,
                        name = "person_fk"))
    private Person person;

    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "person_provider_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "person_provider_fk"))
    private Person person_provider;

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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Person getPerson_provider() {
        return person_provider;
    }

    public void setPerson_provider(Person person_provider) {
        this.person_provider = person_provider;
    }
}
