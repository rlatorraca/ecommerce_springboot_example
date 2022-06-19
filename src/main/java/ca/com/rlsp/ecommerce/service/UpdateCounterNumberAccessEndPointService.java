package ca.com.rlsp.ecommerce.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UpdateCounterNumberAccessEndPointService {

    private JdbcTemplate jdbcTemplate;

    public UpdateCounterNumberAccessEndPointService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveIntoDbEndPointAccess(String endPointName) {
        jdbcTemplate.execute("begin; update endpoint_access_counter set quantity_access = quantity_access + 1 where endpoint_name = '"+ endPointName +"'; commit;");
    }
}
