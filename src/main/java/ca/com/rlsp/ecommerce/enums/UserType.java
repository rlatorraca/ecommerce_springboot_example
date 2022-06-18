package ca.com.rlsp.ecommerce.enums;

public enum UserType {

    MANAGER("Manager"),
    CLIENT("Client"),
    EMPLOYEE("Employee"),
    USER("USER");

    private String description;

    private UserType(String description) {
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
