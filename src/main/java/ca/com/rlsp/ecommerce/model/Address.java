package ca.com.rlsp.ecommerce.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) /* Criar tabelas para as classes concretas / filhas */
@SequenceGenerator(name = "seq_address", sequenceName = "seq_address", initialValue = 1 , allocationSize = 1)
public class Address implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_address")
    private Long id;

    @Column(name = "address_line_01")
    private String addressLine01;
    @Column(name = "address_line_02")
    private String addressLine02;
    @Column(name = "zip_postal_code")
    private String zipPostalCode;
    private String number;
    private String neighborhood;
    private String country;
    private String province;
    private String city;

    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "person_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "person_fk"))
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddressLine01() {
        return addressLine01;
    }

    public void setAddressLine01(String addressLine01) {
        this.addressLine01 = addressLine01;
    }

    public String getAddressLine02() {
        return addressLine02;
    }

    public void setAddressLine02(String addressLine02) {
        this.addressLine02 = addressLine02;
    }

    public String getZipPostalCode() {
        return zipPostalCode;
    }

    public void setZipPostalCode(String zipPostalCode) {
        this.zipPostalCode = zipPostalCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address person = (Address) o;

        return id.equals(person.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
