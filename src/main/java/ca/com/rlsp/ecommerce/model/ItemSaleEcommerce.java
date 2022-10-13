package ca.com.rlsp.ecommerce.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "item_sale_ecommerce")
@SequenceGenerator(name = "seq_item_sale_ecommerce", sequenceName = "seq_item_sale_ecommerce", initialValue = 1 , allocationSize =1)
public class ItemSaleEcommerce implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_item_sale_ecommerce")
    private Long id;

    @Column(nullable = false)
    private Double quantity;

    @ManyToOne
    @JoinColumn(name = "product_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "product_fk"))
    private Product product;

    /* COMPANY | EMPRESA */
    @ManyToOne(targetEntity = LegalPerson.class)
    @JoinColumn(name = "ecommerce_company_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "ecommerce_company_fk"))
    private LegalPerson ecommerceCompany;

    @ManyToOne
    @JoinColumn(name = "product_sales_ecommerce_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "product_sales_ecommerce_fk"))
    private ProductSalesEcommerce productSalesEcommerce;

    @ManyToOne
    @JoinColumn(name = "stock_purchase_invoice_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "stock_purchase_invoice_fk"))
    private StockPurchaseInvoice stockPurchaseInvoice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemSaleEcommerce that = (ItemSaleEcommerce) o;

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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductSalesEcommerce getProductSalesEcommerce() {
        return productSalesEcommerce;
    }

    public void setProductSalesEcommerce(ProductSalesEcommerce productSalesEcommerce) {
        this.productSalesEcommerce = productSalesEcommerce;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public LegalPerson getEcommerceCompany() {
        return ecommerceCompany;
    }

    public void setEcommerceCompany(LegalPerson ecommerceCompany) {
        this.ecommerceCompany = ecommerceCompany;
    }
}

