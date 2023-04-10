package ca.com.rlsp.ecommerce.model.dto;

import java.io.Serializable;

public class TransportationCompanyDTO implements Serializable {

    private String id;
    private String name;
    private String price;
    private String company;
    private String companyLogo;

    public boolean dataOk() {
        return id != null && name != null && price != null && company != null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }
}
