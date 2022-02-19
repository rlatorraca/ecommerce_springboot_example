package ca.com.rlsp.ecommerce.enums;

public enum PersonType {

    MANAGER("Manager"),
    CLIENT("Client"),
    EMPLOYEE("Employee");

    private String description;

    private PersonType(String description) {
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
