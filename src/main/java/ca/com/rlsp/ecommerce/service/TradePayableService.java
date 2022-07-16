package ca.com.rlsp.ecommerce.service;

import ca.com.rlsp.ecommerce.model.Product;
import ca.com.rlsp.ecommerce.model.TradePayable;
import ca.com.rlsp.ecommerce.repository.ProductRepository;
import ca.com.rlsp.ecommerce.repository.TradePayableRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class TradePayableService {

    private TradePayableRepository tradePayableRepository;

    public TradePayableService(TradePayableRepository tradePayableRepository) {
        this.tradePayableRepository = tradePayableRepository;
    }

    public TradePayable saveTradePayable(TradePayable tradePayable) {

        return tradePayableRepository.save(tradePayable);
    }

    public Boolean isTradePayableIntoDB(String tradePayable){
        return tradePayableRepository.isTradePayableIntoDB(tradePayable);
    }

    public Optional<TradePayable> getTradePayableById(Long tradePayableId) {

        return Optional.ofNullable(tradePayableRepository.findById(tradePayableId).orElse(null));
    }
    public void deleteTradePayableByIdById(Long id) {
        tradePayableRepository.deleteById(id);
    }

   public boolean isTradePayablePresentIntoDB(TradePayable productCategory) {
        return tradePayableRepository.findById(productCategory.getId()).isPresent() == true;
    }

   public List<TradePayable> getTradePayableByDescription(String description) {
        return tradePayableRepository.getTradePayableByDescription(description);
    }


    public Collection<TradePayable> getAllTradePayable() {

        return tradePayableRepository.findAll();
    }
}
