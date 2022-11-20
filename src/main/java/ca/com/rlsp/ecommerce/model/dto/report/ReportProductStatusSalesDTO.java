package ca.com.rlsp.ecommerce.model.dto.report;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class ReportProductStatusSalesDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "Informa a data inicial")
    private String initialDate;

    @NotEmpty(message = "Informa a data final")
    private String finalDate;

    private String productCode ="";
    private String productName ="";
    private String clientEmail ="";
    private String clientPhone ="";
    private String procuctValue ="";
    private String clientCode ="";
    private String clientName ="";
    private String qtdEstoque ="";
    private String stockQuantity ="";
    private String statusProductSale ="";

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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getProcuctValue() {
        return procuctValue;
    }

    public void setProcuctValue(String procuctValue) {
        this.procuctValue = procuctValue;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(String qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public String getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(String stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getStatusProductSale() {
        return statusProductSale;
    }

    public void setStatusProductSale(String statusProductSale) {
        this.statusProductSale = statusProductSale;
    }
}
