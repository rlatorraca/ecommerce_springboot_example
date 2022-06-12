package ca.com.rlsp.ecommerce.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class CounterNumberAccessEndPointService {

    private JdbcTemplate jdbcTemplate;

    public CounterNumberAccessEndPointService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveIntoDbEndPointAccess(String endPointName) {
        jdbcTemplate.execute("begin; update end_points_counter set quantity_access = quantity_access + 1 where end_point_name = '"+ endPointName +"'; commit;");
    }
}
