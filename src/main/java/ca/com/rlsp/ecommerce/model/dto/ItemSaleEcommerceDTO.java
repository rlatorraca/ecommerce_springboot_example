package ca.com.rlsp.ecommerce.model.dto;

import ca.com.rlsp.ecommerce.model.Product;

public class ItemSaleEcommerceDTO {

    private Double quantity;

    private ProductDTO product;

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }
}
