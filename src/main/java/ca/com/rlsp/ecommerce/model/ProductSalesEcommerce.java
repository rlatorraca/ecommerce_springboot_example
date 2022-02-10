package ca.com.rlsp.ecommerce.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "product_sales_ecommerce")
@SequenceGenerator(name = "seq_product_sales_ecommerce", sequenceName = "seq_product_sales_ecommerce", initialValue = 1 , allocationSize = 1)
public class ProductSalesEcommerce implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_product_sales_ecommerce")
    private Long id;


}
