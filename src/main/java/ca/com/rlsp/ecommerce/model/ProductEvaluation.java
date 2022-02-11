package ca.com.rlsp.ecommerce.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "product_evaluation")
@SequenceGenerator(name = "seq_product_evaluation", sequenceName = "seq_seq_product_evaluation", initialValue = 1 , allocationSize =1)
public class ProductEvaluation implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_item_sale_ecommerce")
    private Long id;

    private Integer grade;

    @ManyToOne
    @JoinColumn(name = "product_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "product_fk"))
    private Product product;

    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "person_id",
                nullable = false,
                foreignKey = @ForeignKey(
                        value = ConstraintMode.CONSTRAINT,
                        name = "person_fk"))
    private Person person;

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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
