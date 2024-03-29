package ca.com.rlsp.ecommerce.enums;

public enum PersonType {
    LEGALPERSON("Legal Person"),
    NATURALPERSON("Natural Person"),
    SUPPLIER("Supplier");

    private String description;
    PersonType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
