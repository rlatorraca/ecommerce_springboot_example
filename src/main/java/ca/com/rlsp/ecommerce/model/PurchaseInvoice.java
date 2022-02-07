package ca.com.rlsp.ecommerce.model;

import javax.persistence.*;
import java.io.Serializable;

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
}
