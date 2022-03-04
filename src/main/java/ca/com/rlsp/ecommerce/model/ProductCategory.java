package ca.com.rlsp.ecommerce.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "product_category")
@SequenceGenerator(name = "seq_product_category", sequenceName = "seq_product_category", allocationSize = 1, initialValue = 1)
public class ProductCategory implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_product_category")
    private Long id;

    @Column(nullable = false)
    private String description;

    /* COMPANY | EMPRESA */
    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "company_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "company_fk"))
    private Person company;

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

    public Person getCompany() {
        return company;
    }

    public void setCompany(Person company) {
        this.company = company;
    }
}
