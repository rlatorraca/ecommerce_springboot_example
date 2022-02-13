package ca.com.rlsp.ecommerce.repository;

import ca.com.rlsp.ecommerce.model.RoleAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional // Manage all DB transactions
public interface RoleAccessRepository extends JpaRepository<RoleAccess, Long> {

}
