package ca.com.rlsp.ecommerce.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "stock_purchase_invoice_item_product")
@SequenceGenerator(name = "seq_invoice_item_product", sequenceName = "seq_invoice_item_product", initialValue = 1 , allocationSize = 1)
public class StockPurchaseInvoiceItemProduct implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_address")
    private Long id;

    @Range(min=1, message = "Insert product quantity")
    @Column(nullable = false)
    private Double quantity;

    @ManyToOne
    @JoinColumn(name = "product_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "product_fk"))
    private Product product;

    @ManyToOne(targetEntity = StockPurchaseInvoice.class)
    @JoinColumn(name = "stock_purchase_invoice_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "stock_purchase_invoice_fk"))
    private StockPurchaseInvoice stockPurchaseinvoice;

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
        StockPurchaseInvoiceItemProduct that = (StockPurchaseInvoiceItemProduct) o;
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

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public StockPurchaseInvoice getStockPurchaseinvoice() {
        return stockPurchaseinvoice;
    }

    public void setStockPurchaseinvoice(StockPurchaseInvoice stockPurchaseinvoice) {
        this.stockPurchaseinvoice = stockPurchaseinvoice;
    }

    public LegalPerson getEcommerceCompany() {
        return ecommerceCompany;
    }

    public void setEcommerceCompany(LegalPerson ecommerceCompany) {
        this.ecommerceCompany = ecommerceCompany;
    }
}
