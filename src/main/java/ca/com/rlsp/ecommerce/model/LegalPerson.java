package ca.com.rlsp.ecommerce.model;

import org.hibernate.validator.constraints.br.CNPJ;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "legal_person")
@Table(name = "legal_person")
@PrimaryKeyJoinColumn(name = "id")
public class LegalPerson extends Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @CNPJ(message = "Businness number is not valid")
    @Column(name = "business_number", nullable = false)
    private String businessNumber;

    @Column(name = "province_registration", nullable = false)
    private String provinceRegistration;

    @Column(name = "city_registration")
    private String cityRegistration;

    @Column(name = "comercial_name", nullable = false)
    private String comercialName;

    @Column(name = "legal_name", nullable = false)
    private String legalName;

    private String category;


    public String getBusinessNumber() {
        return businessNumber;
    }

    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
    }

    public String getProvinceRegistration() {
        return provinceRegistration;
    }

    public void setProvinceRegistration(String provinceRegistration) {
        this.provinceRegistration = provinceRegistration;
    }

    public String getCityRegistration() {
        return cityRegistration;
    }

    public void setCityRegistration(String cityRegistration) {
        this.cityRegistration = cityRegistration;
    }

    public String getComercialName() {
        return comercialName;
    }

    public void setComercialName(String comercialName) {
        this.comercialName = comercialName;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}
