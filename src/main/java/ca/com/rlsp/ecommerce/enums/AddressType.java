package ca.com.rlsp.ecommerce.enums;

public enum AddressType {

    BILLING("Billing"),
    COMMERCIAL("Commercial"),
    DELIVERY("Delivery");

    private String description;

    AddressType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
