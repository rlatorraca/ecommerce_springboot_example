package ca.com.rlsp.ecommerce.model.dto;

import ca.com.rlsp.ecommerce.model.Address;
import ca.com.rlsp.ecommerce.model.Person;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductSalesEcommerceDTO {

    private Long id;
    private BigDecimal totalValue;
    private BigDecimal totalDiscount;
    private Person person;
    private Address billing;
    private Address delivering;
    private BigDecimal deliveryValue;

    private List<ItemSaleEcommerceDTO> itemsSaleEccommerceDTO = new ArrayList<ItemSaleEcommerceDTO>();

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public BigDecimal getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(BigDecimal totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Address getBilling() {
        return billing;
    }

    public void setBilling(Address billing) {
        this.billing = billing;
    }

    public Address getDelivering() {
        return delivering;
    }

    public void setDelivering(Address delivering) {
        this.delivering = delivering;
    }

    public BigDecimal getDeliveryValue() {
        return deliveryValue;
    }

    public void setDeliveryValue(BigDecimal deliveryValue) {
        this.deliveryValue = deliveryValue;
    }

    public List<ItemSaleEcommerceDTO> getItemsSaleEccommerceDTO() {
        return itemsSaleEccommerceDTO;
    }

    public void setItemsSaleEccommerceDTO(List<ItemSaleEcommerceDTO> itemsSaleEccommerceDTO) {
        this.itemsSaleEccommerceDTO = itemsSaleEccommerceDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
