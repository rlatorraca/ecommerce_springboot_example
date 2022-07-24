package ca.com.rlsp.ecommerce.model.dto;

import ca.com.rlsp.ecommerce.model.LegalPerson;
import ca.com.rlsp.ecommerce.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

public class ProductImageDTO implements Serializable {

    private Long id;
    private String sourceImage;
    private String thumbnail;
    private Long product;
    private Long ecommerceCompany;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceImage() {
        return sourceImage;
    }

    public void setSourceImage(String sourceImage) {
        this.sourceImage = sourceImage;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Long getProduct() {
        return product;
    }

    public void setProduct(Long product) {
        this.product = product;
    }

    public Long getEcommerceCompany() {
        return ecommerceCompany;
    }

    public void setEcommerceCompany(Long ecommerceCompany) {
        this.ecommerceCompany = ecommerceCompany;
    }
}
