package ca.com.rlsp.ecommerce.model.dto.report;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class ReportStockPurchaseInvoiceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String productName ="";

    @NotEmpty(message = "Enter a initial date ")
    private String initialDate;

    @NotEmpty(message = "Enter a final date ")
    private String finalDate;
    private String invoiceCode ="";
    private String productCode ="";
    private String valueProductSale ="";
    private String quantityPurchased ="";
    private String ProviderCode ="";
    private String ProviderName ="";
    private String dateSale ="";

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(String initialDate) {
        this.initialDate = initialDate;
    }

    public String getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(String finalDate) {
        this.finalDate = finalDate;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getValueProductSale() {
        return valueProductSale;
    }

    public void setValueProductSale(String valueProductSale) {
        this.valueProductSale = valueProductSale;
    }

    public String getQuantityPurchased() {
        return quantityPurchased;
    }

    public void setQuantityPurchased(String quantityPurchased) {
        this.quantityPurchased = quantityPurchased;
    }

    public String getProviderCode() {
        return ProviderCode;
    }

    public void setProviderCode(String providerCode) {
        ProviderCode = providerCode;
    }

    public String getProviderName() {
        return ProviderName;
    }

    public void setProviderName(String providerName) {
        ProviderName = providerName;
    }

    public String getDateSale() {
        return dateSale;
    }

    public void setDateSale(String dateSale) {
        this.dateSale = dateSale;
    }
}
