package ca.com.rlsp.ecommerce.repository;

/* REPOSTITORY usado para autenticamento de usuarios com JWT */

import ca.com.rlsp.ecommerce.model.UserSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserSystemRepository extends JpaRepository<UserSystem, Long> {

    @Query(value = "SELECT us FROM UserSystem us " +
                   "WHERE us.login = ?1")
    UserSystem findUserSystemByLogin(String login);

    @Query(value = "SELECT us FROM UserSystem us " +
                   "WHERE us.person.id = ?1 or us.login = ?2")
    UserSystem findUserSystemByPerson(Long id, String email);

    // RAW SQL
    @Query(nativeQuery = true,
           value = "SELECT constraint_name FROM information_schema.constraint_column_usage " +
                   "WHERE table_name='user_role_access' AND column_name='role_access_id' " +
                   "AND constraint_name <> 'unique_access_user';"
           )
    String queryConstraintUserRoleAcessoTable();


    @Transactional
    @Modifying // Deve-se usar para INSERT, DELETE, UPDATE
    // RAW SQL
    @Query(nativeQuery = true,
           value = "INSERT INTO user_role_access(user_system_id, role_access_id) " +
                   "VALUES (?1, (SELECT id FROM role_access WHERE description='ROLE_USER'))"
          )
    void insertStandardUserNatualAndLegalPerson(Long id);

    @Transactional
    @Modifying // Deve-se usar para INSERT, DELETE, UPDATE
    // RAW SQL
    @Query(nativeQuery = true,
            value = "INSERT INTO user_role_access(user_system_id, role_access_id) " +
                    "VALUES (?1, (SELECT id FROM role_access WHERE description=?2 limit 1))"
    )
    void insertStandardUserLegalPerson(Long id, String role);

    /* Pega todos os usuarios que ja tem 90 dias sem trocar a senha*/
    @Query(value = "SELECT us from UserSystem us WHERE (current_date - 90) > us.lastPasswordDate  ")
    List<UserSystem> userPasswordMoreThan90Days();

}
