package ca.com.rlsp.ecommerce.service;


import ca.com.rlsp.ecommerce.model.TrackingStatus;
import ca.com.rlsp.ecommerce.repository.TrackingStatusRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TrackingStatusService {

    private TrackingStatusRepository trackingStatusRepository;

    public TrackingStatusService(TrackingStatusRepository trackingStatusRepository ) {
        this.trackingStatusRepository = trackingStatusRepository;

    }

    public void save(TrackingStatus trackingStatus) {
        trackingStatusRepository.saveAndFlush(trackingStatus);
    }


}
