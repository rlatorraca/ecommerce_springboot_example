package ca.com.rlsp.ecommerce.repository;

/* REPOSTITORY usado para autenticamento de usuarios com JWT */

import ca.com.rlsp.ecommerce.model.UserSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserSystemRepository extends JpaRepository<UserSystem, Long> {

    @Query(value = "select us from UserSystem us where us.login = ?1")
    UserSystem findUserSystemByLogin(String login);

    @Query(value = "select us from UserSystem us where us.person.id = ?1 or us.login = ?2")
    UserSystem findUserSystemByPerson(Long id, String email);

    @Query(value = "select constraint_name from information_schema.constraint_column_usage " +
                   "where table_name='user_role_access' and column_name='role_access_id' " +
                   "and constraint_name <> 'unique_access_user';",
           nativeQuery = true)
    String queryConstraintUserRoleAcessoTable();

    @Transactional
    @Modifying // Deve-se usar para INSERT, DELETE, UPDATE
    @Query(value = "INSERT INTO user_role_access(user_system_id, role_access_id) " +
                   " values (?1, (SELECT id FROM role_access WHERE description = 'ROLE_USER' ))",
           nativeQuery = true)
    void insertStandardUserLegalPerson(Long id);
}
