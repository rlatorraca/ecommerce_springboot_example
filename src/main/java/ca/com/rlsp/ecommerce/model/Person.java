package ca.com.rlsp.ecommerce.model;

import ca.com.rlsp.ecommerce.enums.PersonType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) /* Criar tabelas para as classes concretas / filhas */
@SequenceGenerator(name = "seq_person", sequenceName = "seq_person", initialValue = 1 , allocationSize = 1)
public abstract class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_person")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String telephone;

    @Column(name = "person_type")
    @Enumerated(EnumType.STRING)
    private PersonType personType;

    /* COMPANY | EMPRESA */
    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "ecommerce_company_id",
            nullable = true,
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "ecommerce_company_fk"))
    private Person ecommerceCompany;

    @OneToMany(mappedBy = "person", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Address> addresses = new ArrayList<Address>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public PersonType getPersonType() {
        return personType;
    }

    public void setPersonType(PersonType personType) {
        this.personType = personType;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public Person getEcommerceCompany() {
        return ecommerceCompany;
    }

    public void setEcommerceCompany(Person ecommerceCompany) {
        this.ecommerceCompany = ecommerceCompany;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        return id.equals(person.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }


}
