package ca.com.rlsp.ecommerce.model.dto;

import java.math.BigDecimal;

public class ProductSalesEcommerceDTO {

    private BigDecimal totalValue;

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }
}
