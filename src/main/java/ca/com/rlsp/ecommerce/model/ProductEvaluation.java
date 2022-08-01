package ca.com.rlsp.ecommerce.model;




import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Entity
@Table(name = "product_evaluation")
@SequenceGenerator(name = "seq_product_evaluation", sequenceName = "seq_seq_product_evaluation", initialValue = 1 , allocationSize =1)
public class ProductEvaluation implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_item_sale_ecommerce")
    private Long id;

    @Min(value = 1, message = "Minimum evaluation is 1")
    @Max(value = 10, message = "Maximum evaluation is 10")
    @Column(nullable = false)
    private Integer grade;

    @Column(nullable = false)
    private String description;


    @ManyToOne
    @JoinColumn(name = "product_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "product_fk"))
    private Product product;

    @ManyToOne(targetEntity = NaturalPerson.class)
    @JoinColumn(name = "person_id",
                nullable = false,
                foreignKey = @ForeignKey(
                        value = ConstraintMode.CONSTRAINT,
                        name = "person_fk"))
    private NaturalPerson person;

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

        ProductEvaluation that = (ProductEvaluation) o;

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

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    @JsonIgnore
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @JsonIgnore
    public NaturalPerson getPerson() {
        return person;
    }

    public void setPerson(NaturalPerson person) {
        this.person = person;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonIgnore
    public LegalPerson getEcommerceCompany() {
        return ecommerceCompany;
    }

    public void setEcommerceCompany(LegalPerson ecommerceCompany) {
        this.ecommerceCompany = ecommerceCompany;
    }
}
