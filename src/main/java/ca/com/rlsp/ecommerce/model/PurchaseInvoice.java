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

    @ManyToOne(targetEntity = TradePayable.class)
    @JoinColumn(name = "trade_payable_id",
                nullable = false,
                foreignKey = @ForeignKey(
                        value = ConstraintMode.CONSTRAINT,
                        name = "trade_payable_fk"))
    private TradePayable tradePayable;
}
