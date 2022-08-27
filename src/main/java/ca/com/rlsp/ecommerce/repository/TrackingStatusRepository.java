package ca.com.rlsp.ecommerce.repository;

import ca.com.rlsp.ecommerce.model.TrackingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface TrackingStatusRepository extends JpaRepository<TrackingStatus, Long> {

    @Query(value = "SELECT s from TrackingStatus s WHERE s.productSalesEcommerce.id = ?1 order by s.id")
    public List<TrackingStatus> getAllTrackingStatus(Long saleId);

}
