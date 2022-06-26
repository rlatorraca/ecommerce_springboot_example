package ca.com.rlsp.ecommerce.model.dto;

import ca.com.rlsp.ecommerce.model.Person;

import javax.persistence.*;
import java.io.Serializable;

public class ProductCategoryDTO implements Serializable {

    private Long id;

    private String description;

    private Long ecommerceCompany;

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

    public Long getEcommerceCompany() {
        return ecommerceCompany;
    }

    public void setEcommerceCompany(Long ecommerceCompany) {
        this.ecommerceCompany = ecommerceCompany;
    }
}

