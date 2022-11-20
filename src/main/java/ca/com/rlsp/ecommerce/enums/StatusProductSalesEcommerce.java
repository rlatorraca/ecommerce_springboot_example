package ca.com.rlsp.ecommerce.enums;

public enum StatusProductSalesEcommerce {

    FINALIZED("Finalized"),
    CANCELED("Canceled"),
    ABANDONED_CART("Abandoned cart");

    private String description;

    StatusProductSalesEcommerce(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
