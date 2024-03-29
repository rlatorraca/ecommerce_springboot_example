package ca.com.rlsp.ecommerce.model.dto;

import java.io.Serializable;

public class ProductDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    private String id;
    private String name;
    private String description;
    private Integer stockQuantity;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
