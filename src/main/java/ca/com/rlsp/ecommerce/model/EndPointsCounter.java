package ca.com.rlsp.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "end_points_counter")
public class EndPointsCounter {
    @Id
    private Long id;

    @Column(name = "end_point_name")
    private String endPointName;

    @Column(name = "quantity_access")
    private Long quantity_access;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEndPointName() {
        return endPointName;
    }

    public void setEndPointName(String endPointName) {
        this.endPointName = endPointName;
    }

    public Long getQuantity_access() {
        return quantity_access;
    }

    public void setQuantity_access(Long quantity_access) {
        this.quantity_access = quantity_access;
    }
}
