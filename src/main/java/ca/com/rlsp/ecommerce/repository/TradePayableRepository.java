package ca.com.rlsp.ecommerce.repository;

import ca.com.rlsp.ecommerce.model.TradePayable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TradePayableRepository extends JpaRepository<TradePayable, Long> {

    @Query("SELECT tp FROM TradePayable tp WHERE upper(trim(tp.description)) like %?1%")
    List<TradePayable> getTradePayableByDescription(String desc);


    @Query("SELECT tp FROM TradePayable tp WHERE tp.person.id = ?1")
    List<TradePayable> getTradePayableByPerson(Long personId);


    @Query("SELECT tp FROM TradePayable tp WHERE tp.person_provider.id = ?1")
    List<TradePayable> getTradePayableByProvider(Long providerId);


    @Query("SELECT tp FROM TradePayable tp WHERE tp.ecommerceCompany.id = ?1")
    List<TradePayable> getTradePayableByEcommerceCompany(Long ecommerceId);

    @Query(value = "SELECT COUNT(1) > 0 FROM trade_payable WHERE upper(trim(description)) = upper(trim(?1)) ", nativeQuery = true)
    public Boolean isTradePayableIntoDB(String description);
}
