package ca.com.rlsp.ecommerce.model;

import ca.com.rlsp.ecommerce.enums.UnitType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "product")
@SequenceGenerator(name = "seq_product", sequenceName = "seq_product", initialValue = 1 , allocationSize = 1)
public class Product implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_product")
    private Long id;

    @NotNull(message = "Unit type must be inserted")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UnitType unitType;

    @Size(min = 10, message = "Product name must have at least 10(ten) characters")
    @NotNull(message = "Product name must be inserted")
    @Column(name = "product_name", nullable = false)
    private String name;

    @NotNull(message = "Description must be inserted")
    @Column(columnDefinition = "text", length = 2000, nullable = false)
    private String description;

    /* NOT ITEM NOT Producto - Associar */

    @NotNull(message = "Weight must be inserted")
    @Column(name = "product_weight", nullable = false)
    private Double weight;

    @NotNull(message = "Width must be inserted")
    @Column(name = "product_width", nullable = false)
    private Double width;

    @NotNull(message = "Height must be inserted")
    @Column(name = "product_height", nullable = false)
    private Double height;

    @NotNull(message = "Depth must be inserted")
    @Column(name = "product_depth", nullable = false)
    private Double depth;

    @NotNull(message = "Product value must be inserted")
    @Column(name = "product_value", nullable = false)
    private BigDecimal value = BigDecimal.ZERO;

    @NotNull(message = "Stock quantity must be inserted")
    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity = 0;

    @Column(name = "link_youtube")
    private String linkYoutube;

    @Column(name = "alert_stock_quantity")
    private Boolean alertStockQuantity = Boolean.FALSE;

    @Column(name = "click_quantity")
    private Integer clickQuantity = 0;

    @Column(name = "product_active", nullable = false)
    private Boolean active = Boolean.TRUE;

    /* COMPANY | EMPRESA */
    @NotNull(message = "Legal Person must be inserted")
    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "ecommerce_company_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "ecommerce_company_fk"))
    private LegalPerson ecommerceCompany;

    /* CATEGORY | CATEGORIA */
    @NotNull(message = "Product Category must be inserted")
    @ManyToOne(targetEntity = ProductCategory.class)
    @JoinColumn(name = "product_category_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "product_category_fk"))
    private ProductCategory productCategory;

    /* PRODUCT_BRAND | MARCA_CATEGORIA */
    @NotNull(message = "Product Brand must be inserted")
    @ManyToOne(targetEntity = ProductBrand.class)
    @JoinColumn(name = "product_brand_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "product_brand_fk"))
    private ProductBrand productBrand;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ProductImage> images = new ArrayList<ProductImage>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getDepth() {
        return depth;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getLinkYoutube() {
        return linkYoutube;
    }

    public void setLinkYoutube(String linkYoutube) {
        this.linkYoutube = linkYoutube;
    }

    public Boolean getAlertStockQuantity() {
        return alertStockQuantity;
    }

    public void setAlertStockQuantity(Boolean alertStockQuantity) {
        this.alertStockQuantity = alertStockQuantity;
    }

    public Integer getClickQuantity() {
        return clickQuantity;
    }

    public void setClickQuantity(Integer clickQuantity) {
        this.clickQuantity = clickQuantity;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LegalPerson getEcommerceCompany() {
        return ecommerceCompany;
    }

    public void setEcommerceCompany(LegalPerson ecommerceCompany) {
        this.ecommerceCompany = ecommerceCompany;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public ProductBrand getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(ProductBrand productBrand) {
        this.productBrand = productBrand;
    }

    public List<ProductImage> getImages() {
        return images;
    }

    public void setImages(List<ProductImage> images) {
        this.images = images;
    }
}
