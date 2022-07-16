package ca.com.rlsp.ecommerce.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "product_brand")
@SequenceGenerator(name = "seq_product_brand", sequenceName = "seq_product_brand", allocationSize = 1, initialValue = 1)
public class ProductBrand implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_product_brand")
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull(message = "Inform product brand name or description")
    @Column(nullable = false)
    private String description;

    /* COMPANY | EMPRESA */
    @ManyToOne(targetEntity = LegalPerson.class)
    @JoinColumn(name = "ecommerce_company_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "ecommerce_company_fk"))
    private LegalPerson ecommerceCompany;

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LegalPerson getEcommerceCompany() {
        return ecommerceCompany;
    }

    public void setEcommerceCompany(LegalPerson ecommerceCompany) {
        this.ecommerceCompany = ecommerceCompany;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductBrand that = (ProductBrand) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
