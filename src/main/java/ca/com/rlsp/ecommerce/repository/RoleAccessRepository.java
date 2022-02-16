package ca.com.rlsp.ecommerce.repository;

import ca.com.rlsp.ecommerce.model.RoleAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional // Manage all DB transactions
public interface RoleAccessRepository extends JpaRepository<RoleAccess, Long> {

    @Query("SELECT ra FROM RoleAccess ra where upper(trim(ra.description)) like %?1%")
    List<RoleAccess> findDescriptionAccess(String description);
}
