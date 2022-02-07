package ca.com.rlsp.ecommerce.enums;

public enum UnitType {

    ITERM("item"),
    KG("ilogram"),
    G("gram"),
    PERCENTUAL("Percentaul");


    private String description;

    private UnitType(String description) {
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
