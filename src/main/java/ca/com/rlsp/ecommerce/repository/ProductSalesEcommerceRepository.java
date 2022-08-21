package ca.com.rlsp.ecommerce.repository;

import ca.com.rlsp.ecommerce.model.ProductSalesEcommerce;
import ca.com.rlsp.ecommerce.model.SalesInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
@Transactional
public interface ProductSalesEcommerceRepository extends JpaRepository<ProductSalesEcommerce, Long> {

    @Query(value="SELECT pse FROM ProductSalesEcommerce pse WHERE pse.id = ?1 and pse.deleted = false")
    Optional<ProductSalesEcommerce> findNotDeletedSaleById(Long id);

    @Query(value="SELECT i.productSalesEcommerce FROM ItemSaleEcommerce i WHERE "
            + " i.productSalesEcommerce.deleted = false "
            + " and i.product.id = ?1")
    List<ProductSalesEcommerce> salesByProduct(Long productId);

    @Query(value="SELECT distinct(i.productSalesEcommerce) FROM ItemSaleEcommerce i "
            + " WHERE i.productSalesEcommerce.deleted = false and upper(trim(i.product.name)) like %?1%")
    List<ProductSalesEcommerce> salesByProductName(String value);

    @Query(value="SELECT distinct(i.productSalesEcommerce) FROM ItemSaleEcommerce i "
            + " WHERE i.productSalesEcommerce.deleted = false "
            + " and i.productSalesEcommerce.person.name = ?1 ")
    List<ProductSalesEcommerce> salesByCustomerId(String personName);

    @Query(value="SELECT distinct(i.productSalesEcommerce) FROM ItemSaleEcommerce i "
            + " WHERE i.productSalesEcommerce.deleted = false "
            + " and upper(trim(i.productSalesEcommerce.person.id)) like %?1%"
            + " and trim(i.productSalesEcommerce.person.sinNumber) like %?2%")
    List<ProductSalesEcommerce> salesByCustomerNameAndSIN(String personName, String sinNumber);

    @Query(value="SELECT distinct(i.productSalesEcommerce) FROM ItemSaleEcommerce i "
            + " WHERE i.productSalesEcommerce.deleted = false and upper(trim(i.productSalesEcommerce.person.name)) like %?1%")
    List<ProductSalesEcommerce> salesByCustomerName(String personName);

    @Query(value="select distinct(i.productSalesEcommerce) from ItemSaleEcommerce i "
            + " where i.productSalesEcommerce.deleted = false and upper(trim(i.productSalesEcommerce.person.sinNumber)) like %?1%")
    List<ProductSalesEcommerce> salesByContainSIN(String cpf);


    @Query(value="select distinct(i.productSalesEcommerce) from ItemSaleEcommerce i "
            + " where i.productSalesEcommerce.deleted = false and upper(trim(i.productSalesEcommerce.person.sinNumber)) = ?1")
    List<ProductSalesEcommerce> salesBySIN(String cpf);

    @Query(value="SELECT distinct(i.productSalesEcommerce) FROM ItemSaleEcommerce i "
            + " WHERE i.productSalesEcommerce.deleted = false and upper(trim(i.productSalesEcommerce.billingAddress.addressLine01)) "
            + " like %?1%")
    List<ProductSalesEcommerce> salesByBillingAddress(String billingAddress);


    @Query(value="select distinct(i.productSalesEcommerce) FROM ItemSaleEcommerce i "
            + " WHERE i.productSalesEcommerce.deleted = false and upper(trim(i.productSalesEcommerce.shippingAddress.addressLine01)) "
            + " like %?1%")
    List<ProductSalesEcommerce> salesByShippingAddress(String shippingAddress);


    @org.springframework.transaction.annotation.Transactional
    @Modifying(flushAutomatically = true)
    @Query(nativeQuery = true, value = "UPDATE product_sales_ecommerce SET deleted = true WHERE  id = ?1")
    void deleteLogicalSaleFromDB(Long saleId);

    @org.springframework.transaction.annotation.Transactional
    @Modifying(flushAutomatically = true)
    @Query(nativeQuery = true, value = "UPDATE product_sales_ecommerce SET deleted = false WHERE  id = ?1")
    void revertDeleteLogicalSaleFromDB(Long saleId);


    @Query(value="select distinct(i.productSalesEcommerce) from ItemSaleEcommerce i "
            + " where i.productSalesEcommerce.deleted = false "
            + " and i.productSalesEcommerce.saleDate >= ?1 "
            + " and i.productSalesEcommerce.saleDate <= ?2 ")
    List<ProductSalesEcommerce> getSalesDynamicallyBetweenDates(Date after, Date before);
}