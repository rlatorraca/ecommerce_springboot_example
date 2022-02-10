package ca.com.rlsp.ecommerce.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tracking_status")
@SequenceGenerator(name = "seq_tracking_status", sequenceName = "seq_tracking_status", initialValue = 1 , allocationSize = 1)
public class TrackingStatus implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tracking_status")
    private Long id;

    @Column(name = "distribution_hub")
    private String distributionHub;

    private String city;

    private String province;

    private String country;

    private String status;

    @ManyToOne
    @JoinColumn(name = "product_sales_ecommerce_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "product_sales_ecommerce_fk"))
    private ProductSalesEcommerce productSalesEcommerce;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrackingStatus that = (TrackingStatus) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDistributionHub() {
        return distributionHub;
    }

    public void setDistributionHub(String distributionHub) {
        this.distributionHub = distributionHub;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ProductSalesEcommerce getProductSalesEcommerce() {
        return productSalesEcommerce;
    }

    public void setProductSalesEcommerce(ProductSalesEcommerce productSalesEcommerce) {
        this.productSalesEcommerce = productSalesEcommerce;
    }
}

