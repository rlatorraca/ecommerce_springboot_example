package ca.com.rlsp.ecommerce.enums;

public enum StatusDebtors {

    OPEN("Open bill"),
    OVERDUE("Overdue bill"),
    PAYABLE("Payable bill"),
    PAID("Paid bill");

    private String description;

    private StatusDebtors(String description) {
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
