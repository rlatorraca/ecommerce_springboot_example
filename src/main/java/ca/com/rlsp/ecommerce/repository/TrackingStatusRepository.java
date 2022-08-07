package ca.com.rlsp.ecommerce.repository;

import ca.com.rlsp.ecommerce.model.TrackingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface TrackingStatusRepository extends JpaRepository<TrackingStatus, Long> {



}
