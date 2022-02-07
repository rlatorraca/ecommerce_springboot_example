package ca.com.rlsp.ecommerce.enums;

public enum StatusCreditor {

    OPEN("Open bill"),
    OVERDUE("Overdue bill"),
    PAYABLE("Payable bill"),
    PAID("Paid bill"),
    RENEGOTIATED("Renegotiated bill");

    private String description;

    private StatusCreditor(String description) {
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
