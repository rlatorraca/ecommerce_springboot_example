package ca.com.rlsp.ecommerce.service;

import ca.com.rlsp.ecommerce.model.ProductImage;
import ca.com.rlsp.ecommerce.model.ProductSalesEcommerce;
import ca.com.rlsp.ecommerce.repository.ProductSalesEcommerceRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductSalesEcommerceService {

    private ProductSalesEcommerceRepository productSalesEcommerceRepository;
    private JdbcTemplate jdbcTemplate;

    public ProductSalesEcommerceService(ProductSalesEcommerceRepository productSalesEcommerceRepository,
                                        JdbcTemplate jdbcTemplate) {
        this.productSalesEcommerceRepository = productSalesEcommerceRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public ProductSalesEcommerce findNotDeletedSaleById(Long saleId) {
        return productSalesEcommerceRepository.findNotDeletedSaleById(saleId).orElse(new ProductSalesEcommerce());
    }

    public ProductSalesEcommerce findById(Long saleId) {
        return productSalesEcommerceRepository.findById(saleId).orElse(new ProductSalesEcommerce());
    }

    public ProductSalesEcommerce saveAndFlush(ProductSalesEcommerce productSalesEcommerce) {

        return productSalesEcommerceRepository.saveAndFlush(productSalesEcommerce);
    }
    public void deleteSaleFromDB(Long saleId) {

        String value =
                " BEGIN;" +
                        " UPDATE sales_invoice set product_sales_ecommerce_id = null where product_sales_ecommerce_id = "+saleId+"; " +
                        " delete from sales_invoice where product_sales_ecommerce_id = "+saleId+"; " +
                        " delete from item_sale_ecommerce where product_sales_ecommerce_id = "+saleId+"; " +
                        " delete from tracking_status where product_sales_ecommerce_id = "+saleId+"; " +
                        " delete from product_sales_ecommerce where id = "+saleId+"; "  +
                        " COMMIT; ";

        jdbcTemplate.execute(value);
    }


    public void deleteLogicalSaleFromDB(Long saleId) {
        productSalesEcommerceRepository.deleteLogicalSaleFromDB(saleId);
    }

    public void revertDeleteLogicalSaleFromDB(Long saleId) {
        productSalesEcommerceRepository.revertDeleteLogicalSaleFromDB(saleId);
    }

    public List<ProductSalesEcommerce> salesByProduct(Long productId) {

        return productSalesEcommerceRepository.salesByProduct(productId);
    }
}
