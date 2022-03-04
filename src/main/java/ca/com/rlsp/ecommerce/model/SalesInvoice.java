package ca.com.rlsp.ecommerce.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "sales_invoice")
@SequenceGenerator(name = "seq_sales_invoice", sequenceName = "seq_sales_invoice", initialValue = 1 , allocationSize = 1)
public class SalesInvoice implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_sales_invoice")
    private Long id;

    @Column(name = "invoice_number", nullable = false)
    private String invoiceNumber;

    @Column(name = "invoice_serie", nullable = false)
    private String invoiceSerie;

    @Column(name = "invoice_type", nullable = false)
    private String invoiceType;

    @Column(columnDefinition = "text", nullable = false)
    private String xml;

    @Column(columnDefinition = "text", nullable = false)
    private String pdf;

    @OneToOne
    @JoinColumn(name = "product_sales_ecommerce_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "product_sales_ecommerce_fk"))
    private ProductSalesEcommerce productSalesEcommerce;

    /* COMPANY | EMPRESA */
    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "ecommerce_company_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "ecommerce_company_fk"))
    private Person ecommerceCompany;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SalesInvoice that = (SalesInvoice) o;

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

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public ProductSalesEcommerce getProductSalesEcommerce() {
        return productSalesEcommerce;
    }

    public void setProductSalesEcommerce(ProductSalesEcommerce productSalesEcommerce) {
        this.productSalesEcommerce = productSalesEcommerce;
    }

    public Person getEcommerceCompany() {
        return ecommerceCompany;
    }

    public void setEcommerceCompany(Person ecommerceCompany) {
        this.ecommerceCompany = ecommerceCompany;
    }
}
