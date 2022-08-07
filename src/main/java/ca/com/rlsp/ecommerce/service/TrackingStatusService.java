package ca.com.rlsp.ecommerce.service;


import ca.com.rlsp.ecommerce.model.TrackingStatus;
import ca.com.rlsp.ecommerce.repository.TrackingStatusRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TrackingStatusService {

    private TrackingStatusRepository trackingStatusRepository;
    private JdbcTemplate jdbcTemplate;

    public TrackingStatusService(TrackingStatusRepository trackingStatusRepository,
                                 JdbcTemplate jdbcTemplate) {
        this.trackingStatusRepository = trackingStatusRepository;
        this.jdbcTemplate = jdbcTemplate;
    }


    public void save(TrackingStatus trackingStatus) {
        trackingStatusRepository.saveAndFlush(trackingStatus);
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
}
