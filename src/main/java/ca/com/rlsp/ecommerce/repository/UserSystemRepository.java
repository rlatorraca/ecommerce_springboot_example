package ca.com.rlsp.ecommerce.repository;

/* REPOSTITORY usado para autenticamento de usuarios com JWT */

import ca.com.rlsp.ecommerce.model.UserSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSystemRepository extends JpaRepository<UserSystem, Long> {

    @Query(value = "select us from UserSystem us where us.login = ?1")
    UserSystem findUserSystemByLogin(String login);
}
